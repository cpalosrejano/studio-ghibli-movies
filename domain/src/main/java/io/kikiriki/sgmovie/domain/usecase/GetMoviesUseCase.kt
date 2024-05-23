package io.kikiriki.sgmovie.domain.usecase

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() : Flow<List<Movie>> = withContext(dispatcher) {
        return@withContext movieRepository.get().map { movieDataList ->
            movieDataList.map {  movieData ->
                Movie(
                    id = movieData.id,
                    title = movieData.title,
                    originalTitleRomanised = movieData.originalTitleRomanised,
                    image = movieData.image,
                    movieBanner = movieData.movieBanner,
                    description = movieData.description,
                    director = movieData.director,
                    producer = movieData.producer,
                    releaseDate = movieData.releaseDate,
                    runningTime = movieData.runningTime,
                    rtScore = movieData.rtScore,
                    favourite = movieData.favourite
                )
            }
        }
    }

}