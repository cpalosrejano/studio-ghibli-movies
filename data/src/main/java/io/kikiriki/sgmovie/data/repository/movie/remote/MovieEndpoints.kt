package io.kikiriki.sgmovie.data.repository.movie.remote

import io.kikiriki.sgmovie.data.model.MovieRemote
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieEndpoints {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json")
    @GET("movies")
    suspend fun getMovies (
        @Query("lang") lang: String,
        @Query("coproductions") coproductions: Boolean
    ) : List<MovieRemote>
}