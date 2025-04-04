package io.kikiriki.sgmovie.data.repository.movie.remoteVercel

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.exception.RemoteDataSourceException
import io.kikiriki.sgmovie.data.model.movie.MovieRemote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieEndpoints: MovieEndpoints,
    @IODispatcher private val dispatcher: CoroutineDispatcher
)  : MovieRemoteDataSource {

    override suspend fun get(lang: String, coproductions: Boolean): List<MovieRemote> = withContext(dispatcher) {
        try {
            val result = movieEndpoints.getMovies(lang, coproductions)
            return@withContext result
        } catch (failure: Exception) {
            val exception = handleException(failure)
            throw exception
        }
    }

    private fun handleException(exception: Exception) : RemoteDataSourceException {
        return when (exception) {

            is HttpException -> {
                RemoteDataSourceException(
                    code = when (exception.code()) {
                        401 -> { RemoteDataSourceException.Code.UNAUTHORIZED }
                        404 -> { RemoteDataSourceException.Code.RESOURCE_NOT_FOUND }
                        else -> { RemoteDataSourceException.Code.HTTP_UNKNOWN }
                    },
                    message = exception.message.orEmpty()
                )
            }

            is UnknownHostException -> {
                RemoteDataSourceException(
                    code = RemoteDataSourceException.Code.NO_INTERNET_CONNECTION,
                    message = exception.message.orEmpty()
                )
            }

            else -> {
                RemoteDataSourceException(
                    code = RemoteDataSourceException.Code.DEFAULT,
                    message = exception.message.orEmpty()
                )
            }

        }
    }

}