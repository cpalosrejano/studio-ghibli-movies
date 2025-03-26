package io.kikiriki.sgmovie.domain.model

data class StreamingProvider(
    val id: Int,
    val name: String,
    val logo: String
)

data class WatchProviders(
    val link: String? = null,
    val flatrate: List<StreamingProvider> = emptyList(),
    val rent: List<StreamingProvider> = emptyList(),
    val buy: List<StreamingProvider> = emptyList()
)