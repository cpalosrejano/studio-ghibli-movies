package io.kikiriki.sgmovie.domain.movie

import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.data.repository.movie.MovieRepository
import javax.inject.Inject

class UpdateMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movie: Movie) : Result<Boolean> {
        return if (movie.favourite) {
            movieRepository.addFavorite(movie)
        } else {
            movieRepository.deleteFavorite(movie)
        }
    }

}