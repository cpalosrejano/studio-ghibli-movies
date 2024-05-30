package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.data.model.MovieRemote
import io.kikiriki.sgmovie.domain.model.base.GResult

interface MovieRemoteDataSource {
    suspend fun get() : GResult<List<MovieRemote>, Throwable>
}