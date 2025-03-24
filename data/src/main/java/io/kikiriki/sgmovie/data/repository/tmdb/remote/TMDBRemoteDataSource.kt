package io.kikiriki.sgmovie.data.repository.tmdb.remote

import io.kikiriki.sgmovie.data.model.tmdb.WatchProvidersRemote

interface TMDBRemoteDataSource {

    suspend fun getWatchProviders(tmdbId: String) : WatchProvidersRemote

}