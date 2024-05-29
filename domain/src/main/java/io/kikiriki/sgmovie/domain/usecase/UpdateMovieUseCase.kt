package io.kikiriki.sgmovie.domain.usecase

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(movie: Movie) : GResult<Boolean, Throwable> = withContext(dispatcher) {
        return@withContext movieRepository.update(movie)
    }

}