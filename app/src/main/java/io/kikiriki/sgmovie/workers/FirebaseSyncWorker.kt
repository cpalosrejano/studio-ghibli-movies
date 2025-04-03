package io.kikiriki.sgmovie.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import java.util.concurrent.TimeUnit

@HiltWorker
class FirebaseSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: MovieRepository,
    private val remoteConfig: RemoteConfig
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        try {
            
            // avoid to do work if maintenance is enabled
            if (remoteConfig.isMaintenanceEnabled()) {
                return Result.success()
            }

            // get likes from firebase and update in room
            repository.getAllMovieLikes().fold(
                onSuccess = { updatedLikes ->
                    repository.updateAllMovieLikes(updatedLikes)
                },
                onFailure = { error ->
                    error.printStackTrace()
                }
            )

            // reschedule the work with refresh time from remote config
            scheduleNextWork(remoteConfig.getFirestoreRefreshSeconds())

            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }

    private fun scheduleNextWork(seconds: Long) {
        println("kprint: FirebaseSyncWorker.scheduleNextWork( every $seconds seconds ) ")
        val workRequest = OneTimeWorkRequestBuilder<FirebaseSyncWorker>()
            .setInitialDelay(seconds, TimeUnit.SECONDS)
            .addTag(WORKER_NAME)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }

    companion object {
        const val WORKER_NAME = "sync_likes_firebase_room"
    }
}