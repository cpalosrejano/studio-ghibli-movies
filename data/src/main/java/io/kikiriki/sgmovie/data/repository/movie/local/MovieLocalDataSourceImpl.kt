package io.kikiriki.sgmovie.data.repository.movie.local

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.exception.LocalDataSourceException
import io.kikiriki.sgmovie.data.model.MovieLocal
import io.kikiriki.sgmovie.data.repository.movie.MovieLocalDataSource
import io.kikiriki.sgmovie.domain.model.base.GResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher
)  : MovieLocalDataSource {

    override fun get(): Flow<List<MovieLocal>> = flow {
        movieDao.getAll()
            .flowOn(dispatcher)
            .onEach { emit(it) }
            .catch {
                throw LocalDataSourceException(
                    code = LocalDataSourceException.Code.CANNOT_GET_MOVIES,
                    message = it.message.orEmpty()
                )
            }
            .collect()
    }

    override suspend fun insert(movies: List<MovieLocal>): GResult<Boolean, Throwable> = withContext(dispatcher) {
        return@withContext try {
            // get favourites and set to new list
            val favourites = movieDao.getFavourites()
            movies.forEach { newMovie ->
                newMovie.favourite = favourites.find { it.id == newMovie.id }?.favourite ?: false
            }
            // insert new data with old favourites movies
            movieDao.insert(movies)
            GResult.Success(true)

        } catch (failure: Exception) {
            GResult.Error(
                LocalDataSourceException(
                    code = LocalDataSourceException.Code.CANNOT_INSERT_MOVIES,
                    message = failure.localizedMessage.orEmpty()
                )
            )
        }
    }

    override suspend fun update(movie: MovieLocal): GResult<Boolean, Throwable> = withContext(dispatcher) {
        return@withContext try {
            val value = movieDao.updateFavourite(movie) == 1
            GResult.Success(value)
        } catch (failure: Exception) {
            GResult.Error(
                LocalDataSourceException(
                    code = LocalDataSourceException.Code.CANNOT_UPDATE_MOVIE,
                    message = failure.localizedMessage.orEmpty()
                )
            )
        }
    }

}