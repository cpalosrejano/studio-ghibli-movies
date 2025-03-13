package io.kikiriki.sgmovie.data.repository.movie.local

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.exception.LocalDataSourceException
import io.kikiriki.sgmovie.data.model.MovieLocal
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

    override suspend fun insert(movies: List<MovieLocal>): Boolean = withContext(dispatcher) {
        return@withContext try {
            // get favourites and set to new list
            val favourites = movieDao.getFavourites()
            movies.forEach { newMovie ->
                newMovie.favourite = favourites.find { it.id == newMovie.id }?.favourite ?: false
            }
            // insert new data with old favourites movies
            movieDao.insert(movies)
            true

        } catch (failure: Exception) {
            throw LocalDataSourceException(
                code = LocalDataSourceException.Code.CANNOT_INSERT_MOVIES,
                message = failure.localizedMessage.orEmpty()
            )
        }
    }

    override suspend fun update(movie: MovieLocal):Boolean = withContext(dispatcher) {
        return@withContext try {
            movieDao.updateFavourite(movie) == 1
        } catch (failure: Exception) {
            throw LocalDataSourceException(
                code = LocalDataSourceException.Code.CANNOT_UPDATE_MOVIE,
                message = failure.localizedMessage.orEmpty()
            )
        }
    }

}