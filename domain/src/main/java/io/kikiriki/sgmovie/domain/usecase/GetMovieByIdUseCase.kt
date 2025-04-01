package io.kikiriki.sgmovie.domain.usecase

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(movieId: String) : Flow<Movie> = withContext(dispatcher) {
        return@withContext repository.getMovie(movieId)
    }

}