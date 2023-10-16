package io.kikiriki.sgmovie.domain.movie

import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.data.repository.movie.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetMoviesFlowUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke() : Flow<Result<List<Movie>>> = flow {
        movieRepository.getFavoritesFlow()
            .onEach { value -> emit(value) }
            .catch { exception -> emit(Result.failure(exception)) }
            .collect()
    }

}