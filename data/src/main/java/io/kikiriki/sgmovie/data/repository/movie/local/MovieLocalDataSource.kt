package io.kikiriki.sgmovie.data.repository.movie.local

import io.kikiriki.sgmovie.data.model.movie.MovieLocal
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    fun getAsFlow() : Flow<List<MovieLocal>>
    suspend fun get(): List<MovieLocal>
    fun getMovieById(movieId: String) : Flow<MovieLocal>
    suspend fun insert(movies: List<MovieLocal>) : Boolean
    suspend fun updateLike(movie: MovieLocal) : Boolean
}