package io.kikiriki.sgmovie.data.model.tmdb

import io.kikiriki.sgmovie.domain.model.StreamingProvider

object WatchProviderMapper {

    fun remoteToDomain(remote: Map<String, CountryWatchProviderRemote>) : Map<String, List<StreamingProvider>> {
        return remote.mapValues { entry ->
            entry.value.flatrate?.map { providerRemote ->
                StreamingProvider(
                    id = providerRemote.provider_id,
                    name = providerRemote.provider_name,
                    logo = "https://image.tmdb.org/t/p/w500" + providerRemote.logo_path
                )
            } ?: emptyList()
        }
    }


}