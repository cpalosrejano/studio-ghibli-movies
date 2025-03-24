package io.kikiriki.sgmovie.ui.movie_detail

import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.core.test.BaseTest
import io.kikiriki.sgmovie.data.exception.LocalDataSourceException
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.usecase.GetMovieByIdUseCase
import io.kikiriki.sgmovie.domain.usecase.GetStreamingProviderUseCase
import io.kikiriki.sgmovie.domain.usecase.UpdateMovieLikeUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest : BaseTest() {

    @RelaxedMockK private lateinit var getStreamingProviderUseCase: GetStreamingProviderUseCase
    @RelaxedMockK private lateinit var updateMovieLikeUseCase: UpdateMovieLikeUseCase
    @RelaxedMockK private lateinit var getMovieByIdUseCase: GetMovieByIdUseCase
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onStart() {
        super.onStart()
        movieDetailViewModel = MovieDetailViewModel(
            getStreamingProviderUseCase,
            updateMovieLikeUseCase,
            getMovieByIdUseCase)
    }

    @Test
    fun update_movie_test_error_database_cannot_update() = runBlocking {
        // given
        val movie = Movie(
            id = "dc2e6bd1-8156-4886-adff-b39e6043af0c",
            title = "Spirited Away",
            titleRomanised = "Sen to Chihiro no kamikakushi",
            imageCartel = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            imageBanner = "https://image.tmdb.org/t/p/original/bSXfU4dwZyBA1vMmXvejdRXBvuF.jpg",
            description = "Spirited Away is an Oscar winning Japanese animated film about a ten year old girl who wanders away from her parents along a path that leads to a world ruled by strange and unusual monster-like animals. Her parents have been changed into pigs along with others inside a bathhouse full of these creatures. Will she ever see the world how it once was?",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            soundtrack = "Joe Hisaishi",
            releaseDate = 2001,
            runningTime = 124,
            rtScore = 97,
            coproduction = false,
            like = true,
            tmdbId = "23455"
        )
        val exception = LocalDataSourceException(
            code = LocalDataSourceException.Code.CANNOT_UPDATE_MOVIE,
            message = "random exception message"
        )

        // when
        coEvery { getMovieByIdUseCase("dc2e6bd1-8156-4886-adff-b39e6043af0c") } returns flowOf(movie)
        coEvery { updateMovieLikeUseCase(movie.copy(like = !movie.like)) } returns Result.failure(exception)
        movieDetailViewModel.getMovieById("dc2e6bd1-8156-4886-adff-b39e6043af0c")
        movieDetailViewModel.updateMovieLike()

        // then
        assert(movieDetailViewModel.error.value == R.string.error_bbdd_update_movie)
    }

    @Test
    fun update_movie_test_success() = runBlocking {
        // given
        val movie = Movie(
            id = "dc2e6bd1-8156-4886-adff-b39e6043af0c",
            title = "Spirited Away",
            titleRomanised = "Sen to Chihiro no kamikakushi",
            imageCartel = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            imageBanner = "https://image.tmdb.org/t/p/original/bSXfU4dwZyBA1vMmXvejdRXBvuF.jpg",
            description = "Spirited Away is an Oscar winning Japanese animated film about a ten year old girl who wanders away from her parents along a path that leads to a world ruled by strange and unusual monster-like animals. Her parents have been changed into pigs along with others inside a bathhouse full of these creatures. Will she ever see the world how it once was?",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            soundtrack = "Joe Hisaishi",
            releaseDate = 2001,
            runningTime = 124,
            rtScore = 97,
            coproduction = false,
            like = true,
            tmdbId = "23455"
        )

        // when
        coEvery { getMovieByIdUseCase("dc2e6bd1-8156-4886-adff-b39e6043af0c") } returns flowOf(movie)
        val newMovie = movie.copy(like = false)
        coEvery { updateMovieLikeUseCase(newMovie) } returns Result.success(true)
        coEvery { getMovieByIdUseCase("dc2e6bd1-8156-4886-adff-b39e6043af0c") } returns flowOf(newMovie)
        movieDetailViewModel.getMovieById("dc2e6bd1-8156-4886-adff-b39e6043af0c")
        movieDetailViewModel.updateMovieLike()

        // then
        assert(movieDetailViewModel.error.value == null)
        assert(movieDetailViewModel.movie.value?.like == newMovie.like)

    }

}