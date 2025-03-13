package io.kikiriki.sgmovie.domain.usecase

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() : Flow<GResult<List<Movie>, Throwable>> = withContext(dispatcher) {
        val lang = Locale.getDefault().language
        return@withContext movieRepository.get(lang)
    }

}