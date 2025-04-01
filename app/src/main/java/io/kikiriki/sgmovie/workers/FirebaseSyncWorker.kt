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

    private var seconds = 10L

    override suspend fun doWork(): Result {
        return try {

            println("kprint: FirebaseSyncWorker: starting ")

            //seconds = remoteConfig.getFirestoreRefreshSeconds()

            repository.getAllLikes().fold(
                onSuccess = { updatedLikes ->
                    println("kprint: updateLikes = $updatedLikes ")
                    repository.updateAllLikes(updatedLikes)
                },
                onFailure = { error ->
                    error.printStackTrace()
                }
            )

            println("kprint: FirebaseSyncWorker: success")

            scheduleNextWork()

            Result.success()
        } catch (e: Exception) {
            println("kprint: FirebaseSyncWorker: error: ${e.localizedMessage}")
            e.printStackTrace()
            scheduleNextWork()

            Result.retry() // Reintenta si falla
        }
    }

    private fun scheduleNextWork() {
        println("kprint: FirebaseSyncWorker: scheduling next work in  $seconds seconds ")
        val workRequest = OneTimeWorkRequestBuilder<FirebaseSyncWorker>()
            .setInitialDelay(seconds, TimeUnit.SECONDS)
            .addTag("firebase_sync") // Asigna un tag para poder cancelarlo
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}