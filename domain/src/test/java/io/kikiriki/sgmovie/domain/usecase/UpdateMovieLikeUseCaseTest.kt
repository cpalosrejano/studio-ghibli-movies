package io.kikiriki.sgmovie.domain.usecase

import io.kikiriki.sgmovie.core.test.BaseTest
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class UpdateMovieLikeUseCaseTest : BaseTest() {

    @RelaxedMockK private lateinit var movieRepository: MovieRepository
    private lateinit var updateMovieLikeUseCase: UpdateMovieLikeUseCase

    override fun onStart() {
        super.onStart()
        updateMovieLikeUseCase = UpdateMovieLikeUseCase(movieRepository, Dispatchers.IO)
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
        coEvery { movieRepository.updateLike(movie) } returns GResult.Success(true)
        val result = updateMovieLikeUseCase(movie)

        // then
        assert( result is GResult.Success )
        assert( (result as? GResult.Success)?.data == true )
    }

}