package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.model.movie.MovieMapper
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
import kotlinx.coroutines.flow.combine
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

    override fun get(lang: String, coproductions: Boolean, forceRefresh: Boolean): Flow<GResult<List<Movie>, Throwable>> = flow {

        // check if we need to fetch movies from API
        val localMovies = local.get()
        if (localMovies.isEmpty() || forceRefresh) {

            // fetch movies from api and store in room
            fetchMovies(lang, coproductions).fold(
                onSuccess = { movies: List<Movie> ->
                    emit(GResult.Success(movies))
                },
                onFailure = { error: Throwable ->
                    emit(GResult.Error(error))
                    return@flow
                }
            )
        }

        // emit local movies with firestore updates as flow
        emitAll(
            local.getAsFlow().map { MovieMapper.localToData(it) }
                .combine(firestore.getLikes()) { movies: List<Movie>, likes ->

                    GResult.Success(
                        movies.map { movie ->
                            val likeCount = likes[movie.id] ?: 0
                            movie.copy(likeCount = likeCount)
                        }
                    )
                }
        )
    }

    override suspend fun updateLike(movie: Movie): Result<Boolean> = withContext(dispatcher) {
        if (Constants.Repository.MOCK) {
            return@withContext mock.updateLike(movie)
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

    override fun getMovieById(movieId: String): Flow<Movie> {
        return local.getMovieById(movieId).map {
                MovieMapper.localToData(it)
            }.combine(firestore.getMovieLikesById(movieId)) { movie, likeCount ->
                movie.copy(likeCount = likeCount)
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