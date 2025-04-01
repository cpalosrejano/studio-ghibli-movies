package io.kikiriki.sgmovie.domain.repository

import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun get(lang: String, coproductions: Boolean = false, forceRefresh: Boolean = false):
            Flow<GResult<List<Movie>, Throwable>> // TODO: refactor name to getMovies

    fun getMovieById(movieId: String) : Flow<Movie> // TODO: refactor name to getMovie

    suspend fun updateLike(movie: Movie) : Result<Boolean> // TODO: refactor name to updateMovies

    suspend fun getAllLikes() : Result<Map<String, Long>>

    suspend fun updateAllLikes(likes: Map<String, Long>) : Result<Boolean>
}