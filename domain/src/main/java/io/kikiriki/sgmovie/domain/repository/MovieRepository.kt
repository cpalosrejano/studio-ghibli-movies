package io.kikiriki.sgmovie.domain.repository

import io.kikiriki.sgmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(
        lang: String,
        forceRefresh: Boolean = false,
        coproductions: Boolean = false,
        api: API,
    ): Flow<Result<List<Movie>>>

    fun getMovie(movieId: String) : Flow<Result<Movie>>

    suspend fun updateMovie(movie: Movie) : Result<Boolean>

    suspend fun getAllMovieLikes() : Result<Map<String, Long>>

    suspend fun updateAllMovieLikes(likes: Map<String, Long>) : Result<Boolean>

    enum class API {
        VERCEL,
        RENDER
    }
}