package io.kikiriki.sgmovie.domain.movie

import io.kikiriki.sgmovie.BaseTest
import io.kikiriki.sgmovie.utils.ExceptionManager
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class UpdateMovieUseCaseTest : BaseTest() {

    @RelaxedMockK private lateinit var movieRepository: io.kikiriki.sgmovie.data.repository.movie.MovieRepository
    private lateinit var updateMovieUseCase: io.kikiriki.sgmovie.domain.usecase.UpdateMovieUseCase

    override fun onStart() {
        super.onStart()
        updateMovieUseCase =
            io.kikiriki.sgmovie.domain.usecase.UpdateMovieUseCase(movieRepository, Dispatchers.IO)
    }

    @Test
    fun update_movie_test_error_database_cannot_update() = runBlocking {
        // given
        val movie = io.kikiriki.sgmovie.domain.model.Movie(
            id = "dc2e6bd1-8156-4886-adff-b39e6043af0c",
            title = "Spirited Away",
            originalTitleRomanised = "Sen to Chihiro no kamikakushi",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            movieBanner = "https://image.tmdb.org/t/p/original/bSXfU4dwZyBA1vMmXvejdRXBvuF.jpg",
            description = "Spirited Away is an Oscar winning Japanese animated film about a ten year old girl who wanders away from her parents along a path that leads to a world ruled by strange and unusual monster-like animals. Her parents have been changed into pigs along with others inside a bathhouse full of these creatures. Will she ever see the world how it once was?",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            releaseDate = 2001,
            runningTime = "124",
            rtScore = 97,
            favourite = true
        )
        val exception = io.kikiriki.sgmovie.data.repository.LocalDataSourceException(
            code = ExceptionManager.Code.BBDD_CANNOT_UPDATE_MOVIE,
            message = "random exception message"
        )

        // when
        coEvery { movieRepository.update(movie) } returns Result.failure(exception)
        val result = updateMovieUseCase(movie)

        // then
        assert( result.isFailure )
        assert( result.exceptionOrNull() is io.kikiriki.sgmovie.data.repository.LocalDataSourceException)

    }

    @Test
    fun update_movie_test_success() = runBlocking {
        // given
        val movie = io.kikiriki.sgmovie.domain.model.Movie(
            id = "dc2e6bd1-8156-4886-adff-b39e6043af0c",
            title = "Spirited Away",
            originalTitleRomanised = "Sen to Chihiro no kamikakushi",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            movieBanner = "https://image.tmdb.org/t/p/original/bSXfU4dwZyBA1vMmXvejdRXBvuF.jpg",
            description = "Spirited Away is an Oscar winning Japanese animated film about a ten year old girl who wanders away from her parents along a path that leads to a world ruled by strange and unusual monster-like animals. Her parents have been changed into pigs along with others inside a bathhouse full of these creatures. Will she ever see the world how it once was?",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            releaseDate = 2001,
            runningTime = "124",
            rtScore = 97,
            favourite = true
        )

        // when
        // when
        coEvery { movieRepository.update(movie) } returns Result.success(true)
        val result = updateMovieUseCase(movie)

        // then
        assert( result.isSuccess )
        assert( result.getOrNull() == true )
    }

}