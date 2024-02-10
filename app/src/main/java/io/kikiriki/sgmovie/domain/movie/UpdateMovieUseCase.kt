package io.kikiriki.sgmovie.domain.movie

import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.data.repository.movie.MovieRepository
import io.kikiriki.sgmovie.framework.hilt.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(movie: Movie) : Result<Boolean> = withContext(dispatcher) {
        return@withContext movieRepository.update(movie)
    }

}