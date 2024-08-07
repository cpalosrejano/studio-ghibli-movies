package io.kikiriki.sgmovie.ui.movie_detail

import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.core.test.BaseTest
import io.kikiriki.sgmovie.data.exception.LocalDataSourceException
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest : BaseTest() {

    @RelaxedMockK private lateinit var updateMovieUseCase: io.kikiriki.sgmovie.domain.usecase.UpdateMovieUseCase
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onStart() {
        super.onStart()
        movieDetailViewModel = MovieDetailViewModel(updateMovieUseCase)
    }

    @Test
    fun update_movie_test_error_database_cannot_update() = runBlocking {
        // given
        val movie = Movie(
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
        val exception = LocalDataSourceException(
            code = LocalDataSourceException.Code.CANNOT_UPDATE_MOVIE,
            message = "random exception message"
        )

        // when
        coEvery { updateMovieUseCase(movie.copy(favourite = !movie.favourite)) } returns GResult.Error(exception)
        movieDetailViewModel.updateMovie(movie)

        // then
        assert(movieDetailViewModel.uiState.value?.error == R.string.error_bbdd_update_movie)
    }

    @Test
    fun update_movie_test_success() = runBlocking {
        // given
        val movie = Movie(
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
        coEvery { updateMovieUseCase(movie.copy(favourite = !movie.favourite)) } returns GResult.Success(true)
        movieDetailViewModel.updateMovie(movie)

        // then
        assert(movieDetailViewModel.uiState.value?.error == null)
        assert(movieDetailViewModel.uiState.value?.movie?.favourite != movie.favourite)
    }

}