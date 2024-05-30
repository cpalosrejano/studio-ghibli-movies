package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.model.mapper.MovieMapper
import io.kikiriki.sgmovie.data.utils.Constants
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remote: MovieRemoteDataSource,
    private val local: MovieLocalDataSource,
    private val mock: MovieMockDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun get(forceRefresh: Boolean): Flow<GResult<List<Movie>, Throwable>> = flow {
        if (Constants.Repository.MOCK) {
           mock.get()
               .flowOn(dispatcher)
               .onEach { emit(it) }
               .catch { throw it }
               .collect()

        } else {

            // start to listen database
            local.get()
                .flowOn(dispatcher)
                .onEach {

                    // if list is empty or force refresh is true, fetch movies from API
                    var fetchResult: GResult<*, Throwable>? = null
                    if (it.isEmpty() || forceRefresh) {
                        fetchResult = fetchAPIMovies()
                    }

                    // map result from database to emit to listeners
                    val listMovie = MovieMapper.localToData(it)

                    if (fetchResult is GResult.Error) {
                        // success with errors
                        emit( GResult.SuccessWithError(listMovie, fetchResult.error) )
                    } else {
                        // success
                        emit( GResult.Success(listMovie) )
                    }

                }
                .catch {
                    emit(GResult.Error(it) )
                }
                .collect()
        }
    }

    private suspend fun fetchAPIMovies() : GResult<Boolean, Throwable> {
        // try to fetch movies from API
        return when (val result = remote.get()) {

            is GResult.SuccessWithError -> {
                val domainMovies = MovieMapper.remoteToData(result.data)
                val localMovies = MovieMapper.dataToLocal(domainMovies)
                local.insert(localMovies)
                GResult.Success(true)
            }
            is GResult.Success -> {
                val domainMovies = MovieMapper.remoteToData(result.data)
                val localMovies = MovieMapper.dataToLocal(domainMovies)
                local.insert(localMovies)
                GResult.Success(true)
            }
            is GResult.Error -> {
                GResult.Error(result.error)
            }
        }
    }

    override suspend fun update(movie: Movie): GResult<Boolean, Throwable> = withContext(dispatcher) {
        if (Constants.Repository.MOCK) {
            return@withContext mock.update(movie)
        }

        return@withContext try {
            val localMovie = MovieMapper.dataToLocal(movie)
            local.update(localMovie)
        } catch (failure: Exception) {
            GResult.Error(failure)
        }
    }

}