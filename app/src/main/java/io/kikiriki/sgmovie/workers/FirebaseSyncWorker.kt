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
        return try {

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

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun scheduleNextWork(seconds: Long) {
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