package io.kikiriki.sgmovie.domain.usecase

import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.StreamingProvider
import io.kikiriki.sgmovie.domain.repository.TMDBRepository
import javax.inject.Inject

class GetStreamingProviderUseCase @Inject constructor(
    private val tmdbRepository: TMDBRepository
) {

    suspend fun invoke(movie: Movie, countryCode: String) : Result<List<StreamingProvider>> {
        tmdbRepository.getStreamingProviders(movie.tmdbId).fold(
            onSuccess = { mapCountryProviders ->
                val providers = mapCountryProviders[countryCode] ?: emptyList()
                return Result.success(providers)
            },
            onFailure = { error ->
                return Result.failure(error)
            }
        )
    }
}