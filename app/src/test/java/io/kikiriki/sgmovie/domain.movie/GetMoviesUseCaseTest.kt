package io.kikiriki.sgmovie.domain.movie

import io.kikiriki.sgmovie.BaseTest
import io.kikiriki.sgmovie.utils.ExceptionManager
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMoviesUseCaseTest : BaseTest() {

    @RelaxedMockK private lateinit var movieRepository: io.kikiriki.sgmovie.data.repository.movie.MovieRepository
    private lateinit var getMoviesUseCase: io.kikiriki.sgmovie.domain.usecase.GetMoviesUseCase

    override fun onStart() {
        super.onStart()
        getMoviesUseCase =
            io.kikiriki.sgmovie.domain.usecase.GetMoviesUseCase(movieRepository, Dispatchers.IO)
    }

    @Test
    fun get_movies_test_error_network() = runBlocking {
        // given
        val exception = io.kikiriki.sgmovie.data.repository.RemoteDataSourceException(
            code = ExceptionManager.Code.NETWORK_UNAUTHORIZED,
            message = "random exception message",
            httpCode = 401
        )

        // when
        var resultException: Throwable? = null
        var resultData: List<io.kikiriki.sgmovie.domain.model.Movie>? = null
        coEvery { movieRepository.get() } returns flow { throw exception }
        getMoviesUseCase().onEach { resultData = it }.catch { resultException = it }.collect()
        delay(100)

        // then
        assert( resultException == exception )
        assert( resultData == null )

    }

    @Test
    fun get_movies_test_error_database_get_movies() = runBlocking {
        // given
        val exception = io.kikiriki.sgmovie.data.repository.LocalDataSourceException(
            code = ExceptionManager.Code.BBDD_CANNOT_GET_MOVIES,
            message = "random exception message",
        )

        // when
        var resultException: Throwable? = null
        var resultData: List<io.kikiriki.sgmovie.domain.model.Movie>? = null
        coEvery { movieRepository.get() } returns flow { throw exception }
        getMoviesUseCase().onEach { resultData = it }.catch { resultException = it }.collect()
        delay(100)

        // then
        assert( resultException == exception )
        assert( resultData == null )

    }

    @Test
    fun get_movies_test_success_empty_list() = runBlocking {
        // given
        val movies = listOf<io.kikiriki.sgmovie.domain.model.Movie>()

        // when
        var resultException: Throwable? = null
        var resultData: List<io.kikiriki.sgmovie.domain.model.Movie>? = null
        coEvery { movieRepository.get() } returns flowOf(movies)
        getMoviesUseCase().onEach { resultData = it }.catch { resultException = it }.collect()
        delay(100)

        // then
        assert( resultException == null )
        assert( resultData == movies )

    }

    @Test
    fun get_movies_test_success() = runBlocking {
        // given
        val movies = listOf(
            io.kikiriki.sgmovie.domain.model.Movie(
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
            io.kikiriki.sgmovie.domain.model.Movie(
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
        var resultException: Throwable? = null
        var resultData: List<io.kikiriki.sgmovie.domain.model.Movie>? = null
        coEvery { movieRepository.get() } returns flowOf(movies)
        getMoviesUseCase().onEach { resultData = it }.catch { resultException = it }.collect()
        delay(100)

        // then
        assert( resultException == null )
        assert( resultData == movies )

    }

}