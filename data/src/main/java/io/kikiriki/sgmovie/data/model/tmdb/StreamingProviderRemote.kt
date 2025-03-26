package io.kikiriki.sgmovie.data.model.tmdb

data class WatchProvidersRemote(
    val results: Map<String, CountryWatchProviderRemote>
)

data class CountryWatchProviderRemote(
    val link: String,
    val flatrate: List<StreamingProviderRemote>?,
    val rent: List<StreamingProviderRemote>?,
    val buy: List<StreamingProviderRemote>?
)

data class StreamingProviderRemote(
    val provider_id: Int,
    val provider_name: String,
    val logo_path: String
)