package io.kikiriki.sgmovie.data.repository.tmdb

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.model.tmdb.WatchProviderMapper
import io.kikiriki.sgmovie.data.repository.tmdb.remote.TMDBRemoteDataSource
import io.kikiriki.sgmovie.domain.model.StreamingProvider
import io.kikiriki.sgmovie.domain.repository.TMDBRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TMDBRepositoryImpl @Inject constructor(
    private val tmdbRemoteDataSource: TMDBRemoteDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : TMDBRepository {

    override suspend fun getStreamingProviders(tmdbId: String): Result<Map<String, List<StreamingProvider>>> = withContext(dispatcher) {
        return@withContext try {
            val remoteResult = tmdbRemoteDataSource.getWatchProviders(tmdbId).results
            val result = WatchProviderMapper.remoteToDomain(remoteResult)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}