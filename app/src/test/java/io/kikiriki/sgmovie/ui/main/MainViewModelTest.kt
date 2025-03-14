package io.kikiriki.sgmovie.ui.main

import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.core.test.BaseTest
import io.kikiriki.sgmovie.data.exception.LocalDataSourceException
import io.kikiriki.sgmovie.data.exception.RemoteDataSourceException
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
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
    private lateinit var getMoviesUseCase: io.kikiriki.sgmovie.domain.usecase.GetMoviesUseCase
    @RelaxedMockK
    private lateinit var updateMovieLikeUseCase: io.kikiriki.sgmovie.domain.usecase.UpdateMovieLikeUseCase

    private lateinit var mainViewModel: MainViewModel

    override fun onStart() {
        super.onStart()
        mainViewModel = MainViewModel(getMoviesUseCase, updateMovieLikeUseCase)
    }

    @Test
    fun get_movies_test_error_network_unauthorized() = runBlocking {
        // given
        val exception = RemoteDataSourceException(
            code = RemoteDataSourceException.Code.UNAUTHORIZED,
            message = "random exception message"
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
            code = RemoteDataSourceException.Code.RESOURCE_NOT_FOUND,
            message = "random exception message"
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
            code = RemoteDataSourceException.Code.DEFAULT,
            message = "random exception message"
        )

        // when
        coEvery { getMoviesUseCase() } returns flow { throw exception }
        mainViewModel.getMovies()
        delay(100)

        // then
        assert(mainViewModel.uiState.value?.error == R.string.default_error)
        assert(mainViewModel.uiState.value?.isLoading == false)
        assert(mainViewModel.uiState.value?.items?.isEmpty() == true)

    }

    @Test
    fun get_movies_test_error_no_internet_connection() = runBlocking {
        // given
        val exception = RemoteDataSourceException(
            code = RemoteDataSourceException.Code.NO_INTERNET_CONNECTION,
            message = "random exception message"
        )

        // when
        coEvery { getMoviesUseCase() } returns flow { throw exception }
        mainViewModel.getMovies()
        delay(100)

        // then
        assert(mainViewModel.uiState.value?.error == R.string.error_network_no_connected)
        assert(mainViewModel.uiState.value?.isLoading == false)
        assert(mainViewModel.uiState.value?.items?.isEmpty() == true)

    }

    @Test
    fun get_movies_test_error_database_get_movies() = runBlocking {
        // given
        val exception = LocalDataSourceException(
            code = LocalDataSourceException.Code.CANNOT_GET_MOVIES,
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
            code = LocalDataSourceException.Code.CANNOT_INSERT_MOVIES,
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
        coEvery { getMoviesUseCase() } returns flowOf(GResult.Success(result))
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
                like = true
            ),
            Movie(
                id = "0440483e-ca0e-4120-8c50-4c8cd9b965d6",
                title = "Princess Mononoke",
                titleRomanised = "Mononoke hime",
                imageCartel = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/jHWmNr7m544fJ8eItsfNk8fs2Ed.jpg",
                imageBanner = "https://image.tmdb.org/t/p/original/6pTqSq0zYIWCsucJys8q5L92kUY.jpg",
                description = "Ashitaka, a prince of the disappearing Ainu tribe, is cursed by a demonized boar god and must journey to the west to find a cure. Along the way, he encounters San, a young human woman fighting to protect the forest, and Lady Eboshi, who is trying to destroy it. Ashitaka must find a way to bring balance to this conflict.",
                director = "Hayao Miyazaki",
                producer = "Toshio Suzuki",
                soundtrack = "Joe Hisaishi",
                releaseDate = 1997,
                runningTime = 134,
                rtScore = 92,
                coproduction = false,
                like = true
            ),
        )

        // when
        coEvery { getMoviesUseCase() } returns flowOf(GResult.Success(movies))
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
            like = true
        )
        val exception = LocalDataSourceException(
            code = LocalDataSourceException.Code.CANNOT_UPDATE_MOVIE,
            message = "random exception message"
        )

        // when
        coEvery { updateMovieLikeUseCase(movie.copy(like = !movie.like)) } returns GResult.Error(exception)
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
            like = true
        )

        // when
        coEvery { updateMovieLikeUseCase(movie.copy(like = !movie.like)) } returns GResult.Success(true)
        mainViewModel.updateMovie(movie)

        // then
        assert(mainViewModel.uiState.value?.error == null)
        assert(mainViewModel.uiState.value?.isLoading == false)
    }

}