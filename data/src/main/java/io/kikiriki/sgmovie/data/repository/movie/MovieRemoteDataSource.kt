package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.data.model.MovieRemote

interface MovieRemoteDataSource {
    suspend fun get() : Result<List<MovieRemote>>
}