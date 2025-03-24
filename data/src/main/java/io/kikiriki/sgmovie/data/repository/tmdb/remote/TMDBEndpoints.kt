package io.kikiriki.sgmovie.data.repository.tmdb.remote

import io.kikiriki.sgmovie.data.BuildConfig
import io.kikiriki.sgmovie.data.model.tmdb.WatchProvidersRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBEndpoints {
    @GET("movie/{movie_id}/watch/providers")
    suspend fun getWatchProviders(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): WatchProvidersRemote
}