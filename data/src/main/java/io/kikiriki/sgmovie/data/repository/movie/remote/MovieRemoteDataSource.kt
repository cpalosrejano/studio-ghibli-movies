package io.kikiriki.sgmovie.data.repository.movie.remote

import io.kikiriki.sgmovie.data.model.MovieRemote

interface MovieRemoteDataSource {
    suspend fun get(lang: String, coproductions: Boolean = false) : List<MovieRemote>
}