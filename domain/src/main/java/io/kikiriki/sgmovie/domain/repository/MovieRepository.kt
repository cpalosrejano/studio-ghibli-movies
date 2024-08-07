package io.kikiriki.sgmovie.domain.repository

import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun get(forceRefresh: Boolean = false) : Flow<GResult<List<Movie>, Throwable>>

    suspend fun update(movie: Movie) : GResult<Boolean, Throwable>

}