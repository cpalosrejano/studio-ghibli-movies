package io.kikiriki.sgmovie.data.repository.movie.remote

import io.kikiriki.sgmovie.common.di.dispatchers.IODispatcher
import io.kikiriki.sgmovie.data.model.remote.MovieRemote
import io.kikiriki.sgmovie.data.repository.RemoteDataSourceException
import io.kikiriki.sgmovie.data.repository.movie.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieEndpoints: MovieEndpoints,
    @IODispatcher private val dispatcher: CoroutineDispatcher
)  : MovieRepository.RemoteDataSource {

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
        if (exception is HttpException) {
            return RemoteDataSourceException(
                code = when (exception.code()) {
                    401 -> { RemoteDataSourceException.Code.UNAUTHORIZED }
                    404 -> { RemoteDataSourceException.Code.RESOURCE_NOT_FOUND }
                    else -> { RemoteDataSourceException.Code.HTTP_UNKNOWN }
                },
                message = exception.message.orEmpty()
            )

        } else {
            return RemoteDataSourceException(
                code = RemoteDataSourceException.Code.DEFAULT,
                message = exception.message.orEmpty()
            )
        }
    }

}