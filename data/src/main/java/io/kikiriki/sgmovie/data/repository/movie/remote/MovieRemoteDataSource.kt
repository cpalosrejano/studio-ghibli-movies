package io.kikiriki.sgmovie.data.repository.movie.remote

import io.kikiriki.sgmovie.data.model.MovieRemote

interface MovieRemoteDataSource {
    suspend fun get() : List<MovieRemote>
}