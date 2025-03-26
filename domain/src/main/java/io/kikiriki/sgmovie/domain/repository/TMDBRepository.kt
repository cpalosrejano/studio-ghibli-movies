package io.kikiriki.sgmovie.domain.repository

import io.kikiriki.sgmovie.domain.model.WatchProviders

interface TMDBRepository {

    suspend fun getStreamingProviders(tmdbId: String) : Result<Map<String, WatchProviders>>

}