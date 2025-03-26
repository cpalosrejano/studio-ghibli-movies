package io.kikiriki.sgmovie.data.model.tmdb

import io.kikiriki.sgmovie.domain.model.StreamingProvider
import io.kikiriki.sgmovie.domain.model.WatchProviders

object WatchProviderMapper {

    fun remoteToDomain(remote: Map<String, CountryWatchProviderRemote>) : Map<String, WatchProviders> {
        return remote.mapValues { entry ->
            val countryProviders = entry.value
            WatchProviders(
                link = countryProviders.link,
                flatrate = countryProviders.flatrate?.map { it.toDomain() } ?: emptyList(),
                rent = countryProviders.rent?.map { it.toDomain() } ?: emptyList(),
                buy = countryProviders.buy?.map { it.toDomain() } ?: emptyList()
            )
        }
    }

    private fun StreamingProviderRemote.toDomain(): StreamingProvider {
        return StreamingProvider(
            id = provider_id,
            name = provider_name,
            logo = "https://image.tmdb.org/t/p/w500$logo_path"
        )
    }

}