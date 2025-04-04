package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.model.movie.MovieMapper
import io.kikiriki.sgmovie.data.repository.movie.firestore.MovieFirestoreDataSource
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSource
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSource
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieRemoteDataSource
import io.kikiriki.sgmovie.data.utils.Constants
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val firestore: MovieFirestoreDataSource,
    private val remote: MovieRemoteDataSource,
    private val local: MovieLocalDataSource,
    private val mock: MovieMockDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun getMovies(lang: String, coproductions: Boolean, forceRefresh: Boolean): Flow<Result<List<Movie>>> = flow {

        // check if we need to fetch movies from API
        val localMovies = local.get()
        if (localMovies.isEmpty() || forceRefresh) {

            // fetch movies from api and store in room
            fetchMovies(lang, coproductions).fold(
                onSuccess = { movies: List<Movie> ->
                    emit(Result.success(movies))
                },
                onFailure = { error: Throwable ->
                    emit(Result.failure(error))
                    return@flow
                }
            )
        }

        // emit local movies with firestore updates as flow
        emitAll(
            local.getAsFlow().map {
                val data = MovieMapper.localToData(it)
                Result.success(data)
            }
        )
    }

    override suspend fun updateMovie(movie: Movie): Result<Boolean> = withContext(dispatcher) {
        if (Constants.Repository.MOCK) {
            return@withContext mock.updateMovie(movie)
        }

        return@withContext try {
            // update local and firestore
            val result = local.updateLike(MovieMapper.dataToLocal(movie))
            firestore.updateLike(movie.id, movie.like)
            Result.success(result)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

    override fun getMovie(movieId: String): Flow<Result<Movie>> {
        return local.getMovieById(movieId).map {
            val data = MovieMapper.localToData(it)
            Result.success(data)
        }
    }

    override suspend fun getAllMovieLikes(): Result<Map<String, Long>> {
        return try { Result.success(firestore.getLikes()) }
        catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun updateAllMovieLikes(likes: Map<String, Long>): Result<Boolean> {
        return try {
            local.updateAllLikes(likes)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get movies from API and save them into local database
     */
    private suspend fun fetchMovies(lang: String, coproductions: Boolean) : Result<List<Movie>> {
        try {
            val movies = MovieMapper.remoteToData(remote.get(lang, coproductions))
            local.insert(MovieMapper.dataToLocal(movies))
            return Result.success(movies)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}