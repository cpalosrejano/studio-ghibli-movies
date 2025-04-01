package io.kikiriki.sgmovie.domain.repository

import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(lang: String, coproductions: Boolean = false, forceRefresh: Boolean = false):
            Flow<GResult<List<Movie>, Throwable>>

    fun getMovie(movieId: String) : Flow<Movie>

    suspend fun updateMovie(movie: Movie) : Result<Boolean>

    suspend fun getAllMovieLikes() : Result<Map<String, Long>>

    suspend fun updateAllMovieLikes(likes: Map<String, Long>) : Result<Boolean>
}