package io.kikiriki.sgmovie.data.repository.movie.local

import io.kikiriki.sgmovie.data.model.local.LocalDataSourceException
import io.kikiriki.sgmovie.data.model.local.MovieLocal
import io.kikiriki.sgmovie.data.repository.movie.MovieRepository
import io.kikiriki.sgmovie.framework.hilt.IODispatcher
import io.kikiriki.sgmovie.utils.ExceptionManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher
)  : MovieRepository.LocalDataSource {

    override fun get(): Flow<List<MovieLocal>> = flow {
        movieDao.getAll()
            .flowOn(dispatcher)
            .onEach { emit(it) }
            .catch {
                throw LocalDataSourceException(
                    ExceptionManager.Code.BBDD_CANNOT_GET_MOVIES,
                    "BBDD_CANNOT_GET_MOVIES"
                )
            }
            .collect()
    }

    override suspend fun insert(movies: List<MovieLocal>): Result<Boolean> = withContext(dispatcher) {
        return@withContext try {
            // get favourites and set to new list
            val favourites = movieDao.getFavourites()
            movies.forEach { newMovie ->
                newMovie.favourite = favourites.find { it.id == newMovie.id }?.favourite ?: false
            }
            // insert new data with old favourites movies
            movieDao.insert(movies)
            Result.success(true)

        } catch (failure: Exception) {
            Result.failure(LocalDataSourceException(
                code = ExceptionManager.Code.BBDD_CANNOT_INSERT_MOVIES,
                message = failure.localizedMessage.orEmpty()
            ))
        }
    }

    override suspend fun update(movie: MovieLocal): Result<Boolean> = withContext(dispatcher) {
        return@withContext try {
            val value = movieDao.updateFavourite(movie) == 1
            Result.success(value)
        } catch (failure: Exception) {
            Result.failure(LocalDataSourceException(
                code = ExceptionManager.Code.BBDD_CANNOT_UPDATE_MOVIE,
                message = failure.localizedMessage.orEmpty()
            ))
        }
    }

}