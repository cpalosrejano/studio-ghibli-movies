package io.kikiriki.sgmovie.domain.usecase

import io.kikiriki.sgmovie.core.test.BaseTest
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.Locale

@ExperimentalCoroutinesApi
class GetMoviesUseCaseTest : BaseTest() {

    @RelaxedMockK private lateinit var movieRepository: MovieRepository
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    private val lang = Locale.getDefault().language
    private val coproductions = false

    override fun onStart() {
        super.onStart()
        getMoviesUseCase = GetMoviesUseCase(movieRepository, Dispatchers.IO)
    }

    @Test
    fun get_movies_test_success_empty_list() = runBlocking {
        // given
        val movies = listOf<Movie>()

        // when
        var resultException: Throwable? = null
        var resultData: List<Movie>? = null

        coEvery { movieRepository.get(lang, coproductions) } returns flowOf(GResult.Success(movies))
        getMoviesUseCase().onEach {
            when (it) {
                is GResult.Success -> { resultData = it.data }
                is GResult.Error -> { resultException = it.error }
                is GResult.SuccessWithError -> { resultException = it.error }
            }
        }.catch {
            resultException = it
        }.collect()
        delay(1000)

        // then
        assert( resultException == null )
        assert( resultData == movies )

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
        var resultException: Throwable? = null
        var resultData: List<Movie>? = null
        coEvery { movieRepository.get(lang) } returns flowOf(GResult.Success(movies))
        getMoviesUseCase().onEach {
            when (it) {
                is GResult.Success -> resultData = it.data
                is GResult.Error -> {}
                is GResult.SuccessWithError -> {}
            }
        }.catch {
            resultException = it
        }.collect()
        delay(100)

        // then
        assert( resultException == null )
        assert( resultData == movies )

    }

}