package io.kikiriki.sgmovie.data.repository.tmdb.remote

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.exception.RemoteDataSourceException
import io.kikiriki.sgmovie.data.model.tmdb.WatchProvidersRemote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class TMDBRemoteDataSourceImpl @Inject constructor(
    private val tmdbEndpoints: TMDBEndpoints,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : TMDBRemoteDataSource {

    override suspend fun getWatchProviders(tmdbId: String): WatchProvidersRemote = withContext(dispatcher) {

        return@withContext try {
            tmdbEndpoints.getWatchProviders(tmdbId)
        } catch (e: Exception) {
            throw handleException(e)
        }
    }

    private fun handleException(exception: Exception) : RemoteDataSourceException {
        return when (exception) {

            is UnknownHostException -> {
                RemoteDataSourceException(
                    code = RemoteDataSourceException.Code.NO_INTERNET_CONNECTION,
                    message = exception.message.orEmpty()
                )
            }

            else -> {
                RemoteDataSourceException(
                    code = RemoteDataSourceException.Code.STREAMING_PROVIDER_NOT_FOUND,
                    message = exception.message.orEmpty()
                )
            }

        }
    }

}