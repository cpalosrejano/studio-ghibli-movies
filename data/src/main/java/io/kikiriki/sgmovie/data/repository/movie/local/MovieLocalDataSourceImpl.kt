package io.kikiriki.sgmovie.data.repository.movie.local

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.exception.LocalDataSourceException
import io.kikiriki.sgmovie.data.model.movie.MovieLocal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher
)  : MovieLocalDataSource {

    override fun getAsFlow(): Flow<List<MovieLocal>> = movieDao.getAll()

    override suspend fun get(): List<MovieLocal> {
        try {
            return movieDao.getAll().firstOrNull() ?: emptyList()
        } catch (e: Exception) {
            throw LocalDataSourceException(
                code = LocalDataSourceException.Code.CANNOT_GET_MOVIES,
                message = e.message.orEmpty()
            )
        }
    }

    override fun getMovieById(movieId: String): Flow<MovieLocal> {
        try {
            return movieDao.getMovieById(movieId)
        } catch (e: Exception) {
            throw LocalDataSourceException(
                code = LocalDataSourceException.Code.CANNOT_GET_MOVIE_DETAIL,
                message = e.message.orEmpty()
            )
        }
    }

    override suspend fun insert(movies: List<MovieLocal>): Boolean = withContext(dispatcher) {
        return@withContext try {
            // get likes and set to new list
            val favourites = movieDao.getMoviesLike()
            movies.forEach { newMovie ->
                newMovie.like = favourites.find { it.id == newMovie.id }?.like ?: false
            }
            // insert new data with old likes movies
            movieDao.insert(movies)
            true

        } catch (failure: Exception) {
            throw LocalDataSourceException(
                code = LocalDataSourceException.Code.CANNOT_INSERT_MOVIES,
                message = failure.localizedMessage.orEmpty()
            )
        }
    }

    override suspend fun updateLike(movie: MovieLocal):Boolean = withContext(dispatcher) {
        return@withContext try {
            movieDao.updateMovieLike(movie) == 1
        } catch (failure: Exception) {
            throw LocalDataSourceException(
                code = LocalDataSourceException.Code.CANNOT_UPDATE_MOVIE,
                message = failure.localizedMessage.orEmpty()
            )
        }
    }

    override suspend fun updateAllLikes(likes: Map<String, Long>): Boolean = withContext(dispatcher) {
        return@withContext try {
            movieDao.updateMovieLikes(likes)
            true
        } catch (failure: Exception) {
            failure.printStackTrace()
            throw LocalDataSourceException(
                code = LocalDataSourceException.Code.CANNOT_UPDATE_MOVIE,
                message = failure.localizedMessage.orEmpty()
            )
        }
    }
}