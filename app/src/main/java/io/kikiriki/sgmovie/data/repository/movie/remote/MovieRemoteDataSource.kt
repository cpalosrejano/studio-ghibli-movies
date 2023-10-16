package io.kikiriki.sgmovie.data.repository.movie.remote

import io.kikiriki.sgmovie.data.model.remote.MovieRemote
import io.kikiriki.sgmovie.data.model.remote.RemoteDataSourceException
import io.kikiriki.sgmovie.data.repository.movie.MovieRepository
import io.kikiriki.sgmovie.utils.ExceptionManager
import retrofit2.HttpException
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieEndpoints: MovieEndpoints
)  : MovieRepository.RemoteDataSource {

    override suspend fun getAll(): Result<List<MovieRemote>> {
        val fields = "id,title,original_title_romanised,image,movie_banner,description,director,producer,release_date,running_time,rt_score"
        val limit = 250
        return try {
            val result = movieEndpoints.getMovies(limit = limit, fields = fields)
            Result.success(result)
        } catch (failure: Exception) {
            val exception = handleException(failure)
            Result.failure(exception)
        }
    }


    private fun handleException(exception: Exception) : Exception {
        if (exception is HttpException) {
            val code = when (exception.code()) {
                401 -> { ExceptionManager.Code.NETWORK_UNAUTHORIZED }
                404 -> { ExceptionManager.Code.NETWORK_NOT_FOUND }
                else -> { ExceptionManager.Code.DEFAULT_ERROR }
            }
            return RemoteDataSourceException(
                code = code,
                message = exception.localizedMessage.orEmpty(),
                httpCode = exception.code()
            )

        } else {
            return RemoteDataSourceException(
                code = ExceptionManager.Code.DEFAULT_ERROR,
                message = exception.message.orEmpty(),
                httpCode = null
            )
        }
    }

}