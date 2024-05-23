package io.kikiriki.sgmovie.domain.repository

import io.kikiriki.sgmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun get() : Flow<List<Movie>>

    suspend fun update(movie: Movie) : Result<Boolean>

}