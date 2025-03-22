package io.kikiriki.sgmovie.domain.repository

import io.kikiriki.sgmovie.domain.model.StreamingProvider

interface TMDBRepository {

    suspend fun getStreamingProviders(tmdbId: String) : Result<Map<String,List<StreamingProvider>>>

}