package io.kikiriki.sgmovie.domain.movie

import androidx.constraintlayout.utils.widget.MockView
import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.data.repository.movie.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke() : Result<List<Movie>> {
        val getAllResult = movieRepository.getAll()
        val getFavouritesResult = movieRepository.getFavorites()

        // get from api and database is ok. update favourite property value
        if (getAllResult.isSuccess && getFavouritesResult.isSuccess) {
            val allMovies = getAllResult.getOrElse { listOf() }
            val favourites = getFavouritesResult.getOrElse { listOf() }
            allMovies.forEach { it.favourite = favourites.contains(it) }
            return Result.success(allMovies)
        }

        // get successfully from API, but database is corrupt
        if (getAllResult.isSuccess && getFavouritesResult.isFailure) {
            // TODO: return data, but show a message
            return getAllResult

        } else {
            // in any other case, return API result
            return getAllResult
        }

    }

}