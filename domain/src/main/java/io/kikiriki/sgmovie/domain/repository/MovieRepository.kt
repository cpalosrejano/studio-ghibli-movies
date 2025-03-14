package io.kikiriki.sgmovie.domain.repository

import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun get(lang: String, coproductions: Boolean = false, forceRefresh: Boolean = false):
            Flow<GResult<List<Movie>, Throwable>>

    fun getMovieById(movieId: String) : Flow<Movie>

    suspend fun updateLike(movie: Movie) : GResult<Boolean, Throwable>

}