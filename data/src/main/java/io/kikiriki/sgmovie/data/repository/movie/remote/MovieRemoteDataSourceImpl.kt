package io.kikiriki.sgmovie.data.repository.movie.remote

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.exception.RemoteDataSourceException
import io.kikiriki.sgmovie.data.model.MovieRemote
import io.kikiriki.sgmovie.data.repository.movie.MovieRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieEndpoints: MovieEndpoints,
    @IODispatcher private val dispatcher: CoroutineDispatcher
)  : MovieRemoteDataSource {

    override suspend fun get(): Result<List<MovieRemote>> = withContext(dispatcher) {
        val fields = "id,title,original_title_romanised,image,movie_banner,description,director,producer,release_date,running_time,rt_score"
        val limit = 250
        return@withContext try {
            val result = movieEndpoints.getMovies(limit = limit, fields = fields)
            Result.success(result)
        } catch (failure: Exception) {
            val exception = handleException(failure)
            Result.failure(exception)
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