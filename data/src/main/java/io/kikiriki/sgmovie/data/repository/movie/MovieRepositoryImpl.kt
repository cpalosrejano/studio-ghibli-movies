package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.exception.RemoteDataSourceException
import io.kikiriki.sgmovie.data.model.mapper.MovieMapper
import io.kikiriki.sgmovie.data.repository.movie.firestore.MovieFirestoreDataSource
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSource
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSource
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieRemoteDataSource
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
    private val firestore: MovieFirestoreDataSource,
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
                .onEach { localMovies ->

                    // if list is empty or force refresh is true, fetch movies from API
                    var fetchResult: GResult<*, Throwable>? = null
                    if (localMovies.isEmpty() || forceRefresh) {
                        fetchResult = fetchMovies()
                    }

                    // map result from database to emit to listeners
                    val movies = MovieMapper.localToData(localMovies)

                    // validate result of fetch
                    when (fetchResult) {
                        is GResult.Error -> {
                            // if fetch contains error, return local movies and error
                            emit( GResult.SuccessWithError(movies, fetchResult.error) )
                        }
                        is GResult.SuccessWithError -> {
                            // if fetch contains error, return local movies and error
                            emit( GResult.SuccessWithError(movies, fetchResult.error) )
                        }
                        else -> {
                            // otherwise success
                            emit( GResult.Success(movies) )
                        }
                    }
                }
                .catch {
                    emit(GResult.Error(it) )
                }
                .collect()
        }
    }

    override suspend fun update(movie: Movie): GResult<Boolean, Throwable> = withContext(dispatcher) {
        if (Constants.Repository.MOCK) {
            return@withContext mock.update(movie)
        }

        return@withContext try {
            val localMovie = MovieMapper.dataToLocal(movie)
            val result = local.update(localMovie)
            GResult.Success(result)
        } catch (failure: Exception) {
            GResult.Error(failure)
        }
    }

    private suspend fun fetchMovies() : GResult<Boolean, Throwable> {
        var remoteError: Throwable? = null
        var firestoreError: Throwable? = null

        // try to fetch movies from API
        try {
            val moviesRemote = remote.get()
            val movies = MovieMapper.remoteToData(moviesRemote)
            upsetMovies(movies)
        } catch (exception: Exception) {
            remoteError = exception
        }

        // try to fetch movies from Firestore
        try {
            val moviesRemote = firestore.get()
            val movies = MovieMapper.firestoreToData(moviesRemote)
            upsetMovies(movies)
        } catch (exception: Exception) {
            firestoreError = exception
        }

        return if (firestoreError != null && remoteError != null) {
            // both request failed, return error
            GResult.Error(remoteError)

        } else if (firestoreError == null && remoteError == null) {
            // both request success, return success
            GResult.Success(true)

        } else {
            // one request failed, return success with error
            val error: Throwable = remoteError ?: firestoreError ?:
            RemoteDataSourceException(RemoteDataSourceException.Code.DEFAULT, "")

            GResult.SuccessWithError(true, error)
        }

    }

    private suspend fun upsetMovies(movies: List<Movie>) {
        val localMovies = MovieMapper.dataToLocal(movies)
        local.insert(localMovies)
    }

}