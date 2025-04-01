package io.kikiriki.sgmovie.domain.usecase

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateMovieLikeUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(movie: Movie) : Result<Boolean> = withContext(dispatcher) {
        val newLikeStatus = !movie.like
        val newLikeCountStatus = movie.likeCount + if (newLikeStatus) +1 else -1
        val newMovieStatus = movie.copy(like = newLikeStatus, likeCount = newLikeCountStatus)
        return@withContext movieRepository.updateMovie(newMovieStatus)
    }

}