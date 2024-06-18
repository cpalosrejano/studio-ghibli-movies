package io.kikiriki.sgmovie.data.repository.movie.local

import io.kikiriki.sgmovie.data.model.MovieLocal
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    fun get() : Flow<List<MovieLocal>>
    /** Insert or update the current movies. This insert respect the value of previously favourites movies*/
    suspend fun insert(movies: List<MovieLocal>) : Boolean
    suspend fun update(movie: MovieLocal) : Boolean
}