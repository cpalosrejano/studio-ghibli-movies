package io.kikiriki.sgmovie.data.repository.movie.local

import io.kikiriki.sgmovie.data.model.local.LocalDataSourceException
import io.kikiriki.sgmovie.data.model.local.MovieLocal
import io.kikiriki.sgmovie.data.repository.movie.MovieRepository
import io.kikiriki.sgmovie.utils.ExceptionManager
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao
)  : MovieRepository.LocalDataSource {

    override suspend fun getFavorites(): Result<List<MovieLocal>> {
        return try {
            val data = movieDao.getFavorites()
            Result.success(data)
        } catch (failure: Exception) {
            Result.failure(LocalDataSourceException(
                code = ExceptionManager.Code.BBDD_CANNOT_GET_FAVORITES,
                message = failure.localizedMessage.orEmpty()
            ))
        }
    }

    override suspend fun addFavorite(movie: MovieLocal): Result<Boolean> {
        return try {
            movieDao.addFavorite(movie)
            Result.success(true)
        } catch (failure: Exception) {
            Result.failure(LocalDataSourceException(
                code = ExceptionManager.Code.BBDD_CANNOT_ADD_FAVORITE,
                message = failure.localizedMessage.orEmpty()
            ))
        }
    }

    override suspend fun deleteFavorite(movie: MovieLocal): Result<Boolean> {
        return try {
            val rowsAffected = movieDao.deleteFavorite(movie)
            Result.success((rowsAffected == 1))
        } catch (failure: Exception) {
            Result.failure(LocalDataSourceException(
                code = ExceptionManager.Code.BBDD_CANNOT_DELETE_FAVORITE,
                message = failure.localizedMessage.orEmpty()
            ))
        }
    }

}