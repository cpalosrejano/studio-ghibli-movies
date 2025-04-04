package io.kikiriki.sgmovie.data.repository.movie.remoteVercel

import io.kikiriki.sgmovie.data.model.movie.MovieRemote

interface MovieRemoteDataSource {
    suspend fun get(lang: String, coproductions: Boolean = false) : List<MovieRemote>
}