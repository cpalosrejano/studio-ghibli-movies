package io.kikiriki.sgmovie.domain.usecase

import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.WatchProviders
import io.kikiriki.sgmovie.domain.repository.TMDBRepository
import javax.inject.Inject

class GetStreamingProviderUseCase @Inject constructor(
    private val tmdbRepository: TMDBRepository
) {

    suspend operator fun invoke(movie: Movie, countryCode: String) : Result<WatchProviders> {
        tmdbRepository.getStreamingProviders(movie.tmdbId).fold(
            onSuccess = { mapCountryProviders ->
                val providers = mapCountryProviders[countryCode] ?: WatchProviders()
                return Result.success(providers)
            },
            onFailure = { error ->
                return Result.failure(error)
            }
        )
    }
}