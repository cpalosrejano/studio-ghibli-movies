package io.kikiriki.sgmovie.ui.main

import io.kikiriki.sgmovie.BaseTest
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.data.model.local.LocalDataSourceException
import io.kikiriki.sgmovie.data.model.remote.RemoteDataSourceException
import io.kikiriki.sgmovie.domain.movie.GetMoviesUseCase
import io.kikiriki.sgmovie.domain.movie.UpdateMovieUseCase
import io.kikiriki.sgmovie.utils.ExceptionManager
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest : BaseTest() {

    @RelaxedMockK
    private lateinit var getMoviesUseCase: GetMoviesUseCase
    @RelaxedMockK
    private lateinit var updateMovieUseCase: UpdateMovieUseCase

    private lateinit var mainViewModel: MainViewModel

    override fun onStart() {
        super.onStart()
        mainViewModel = MainViewModel(getMoviesUseCase, updateMovieUseCase)
    }

    @Test
    fun get_movies_test_error_network_unauthorized() = runBlocking {
        // given
        val exception = RemoteDataSourceException(
            code = ExceptionManager.Code.NETWORK_UNAUTHORIZED,
            message = "random exception message",
            httpCode = 401
        )

        // when
        coEvery { getMoviesUseCase() } returns flow { throw exception }
        mainViewModel.getMovies()
        delay(100)

        // then
        assert(mainViewModel.uiState.value?.error == R.string.error_network_unauthorized)
        assert(mainViewModel.uiState.value?.isLoading == false)
        assert(mainViewModel.uiState.value?.items?.isEmpty() == true)

    }

    @Test
    fun get_movies_test_error_network_not_found() = runBlocking {
        // given
        val exception = RemoteDataSourceException(
            code = ExceptionManager.Code.NETWORK_NOT_FOUND,
            message = "random exception message",
            httpCode = 404
        )

        // when
        coEvery { getMoviesUseCase() } returns flow { throw exception }
        mainViewModel.getMovies()
        delay(100)

        // then
        assert(mainViewModel.uiState.value?.error == R.string.error_network_not_found)
        assert(mainViewModel.uiState.value?.isLoading == false)
        assert(mainViewModel.uiState.value?.items?.isEmpty() == true)

    }

    @Test
    fun get_movies_test_error_network_default() = runBlocking {
        // given
        val exception = RemoteDataSourceException(
            code = ExceptionManager.Code.DEFAULT_ERROR,
            message = "random exception message",
            httpCode = 404
        )

        // when
        coEvery { getMoviesUseCase() } returns flow { throw exception }
        mainViewModel.getMovies()
        delay(100)

        // then
        assert(mainViewModel.uiState.value?.error == R.string.default_remote_error)
        assert(mainViewModel.uiState.value?.isLoading == false)
        assert(mainViewModel.uiState.value?.items?.isEmpty() == true)

    }

    @Test
    fun get_movies_test_error_database_get_movies() = runBlocking {
        // given
        val exception = LocalDataSourceException(
            code = ExceptionManager.Code.BBDD_CANNOT_GET_MOVIES,
            message = "random exception message",
        )

        // when
        coEvery { getMoviesUseCase() } returns flow { throw exception }
        mainViewModel.getMovies()
        delay(100)

        // then
        assert(mainViewModel.uiState.value?.error == R.string.error_bbdd_get_movies)
        assert(mainViewModel.uiState.value?.isLoading == false)
        assert(mainViewModel.uiState.value?.items?.isEmpty() == true)

    }

    @Test
    fun get_movies_test_error_database_insert_movies() = runBlocking {
        // given
        val exception = LocalDataSourceException(
            code = ExceptionManager.Code.BBDD_CANNOT_INSERT_MOVIES,
            message = "random exception message",
        )

        // when
        coEvery { getMoviesUseCase() } returns flow { throw exception }
        mainViewModel.getMovies()
        delay(100)

        // then
        assert(mainViewModel.uiState.value?.error == R.string.error_bbdd_insert_movies)
        assert(mainViewModel.uiState.value?.isLoading == false)
        assert(mainViewModel.uiState.value?.items?.isEmpty() == true)

    }

    @Test
    fun get_movies_test_success_empty_list() = runBlocking {
        // given
        val result = listOf<Movie>()

        // when
        coEvery { getMoviesUseCase() } returns flowOf(result)
        mainViewModel.getMovies()
        delay(100)

        // then
        assert(mainViewModel.uiState.value?.error == null)
        assert(mainViewModel.uiState.value?.isLoading == false)
        assert(mainViewModel.uiState.value?.items?.isEmpty() == true)

    }

    @Test
    fun get_movies_test_success() = runBlocking {
        // given
        val movies = listOf(
            Movie(
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
            ),
            Movie(
                id = "0440483e-ca0e-4120-8c50-4c8cd9b965d6",
                title = "Princess Mononoke",
                originalTitleRomanised = "Mononoke hime",
                image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/jHWmNr7m544fJ8eItsfNk8fs2Ed.jpg",
                movieBanner = "https://image.tmdb.org/t/p/original/6pTqSq0zYIWCsucJys8q5L92kUY.jpg",
                description = "Ashitaka, a prince of the disappearing Ainu tribe, is cursed by a demonized boar god and must journey to the west to find a cure. Along the way, he encounters San, a young human woman fighting to protect the forest, and Lady Eboshi, who is trying to destroy it. Ashitaka must find a way to bring balance to this conflict.",
                director = "Hayao Miyazaki",
                producer = "Toshio Suzuki",
                releaseDate = 1997,
                runningTime = "134",
                rtScore = 92,
                favourite = true
            ),
        )

        // when
        coEvery { getMoviesUseCase() } returns flowOf(movies)
        mainViewModel.getMovies()
        delay(100)

        // then
        assert(mainViewModel.uiState.value?.error == null)
        assert(mainViewModel.uiState.value?.isLoading == false)
        assert(mainViewModel.uiState.value?.items?.isEmpty() == false)
        assert(mainViewModel.uiState.value?.items?.size == movies.size)

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
            code = ExceptionManager.Code.BBDD_CANNOT_UPDATE_MOVIE,
            message = "random exception message"
        )

        // when
        coEvery { updateMovieUseCase(movie.copy(favourite = !movie.favourite)) } returns Result.failure(
            exception
        )
        mainViewModel.updateMovie(movie)

        // then
        assert(mainViewModel.uiState.value?.error == R.string.error_bbdd_update_movie)
        assert(mainViewModel.uiState.value?.isLoading == false)

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
        coEvery { updateMovieUseCase(movie.copy(favourite = !movie.favourite)) } returns Result.success(true)
        mainViewModel.updateMovie(movie)

        // then
        assert(mainViewModel.uiState.value?.error == null)
        assert(mainViewModel.uiState.value?.isLoading == false)
    }

}