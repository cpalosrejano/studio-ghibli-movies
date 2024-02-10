package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.data.repository.movie.local.MovieDao
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSource
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSource
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieEndpoints
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieRemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test

class MovieRepositoryTest {

    @RelaxedMockK private lateinit var endpoints: MovieEndpoints
    @RelaxedMockK private lateinit var dao: MovieDao

    private lateinit var repository: MovieRepository

    @Before
    fun onStart() {
        MockKAnnotations.init(this)

        // build repository
        repository = MovieRepositoryImpl(
            remote = MovieRemoteDataSource(endpoints, Dispatchers.Unconfined),
            local = MovieLocalDataSource(dao, Dispatchers.Unconfined),
            mock = MovieMockDataSource(Dispatchers.Unconfined),
            dispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun check_if_get_remote_then_success() {
        // given

        // when

        // then
    }

    @Test
    fun check_if_get_remote_then_error_parse_response() {
        // given

        // when

        // then
    }

    @Test
    fun check_if_get_remote_then_error_resource_not_found() {
        // given

        // when

        // then
    }

    @Test
    fun check_if_get_remote_then_error_no_internet_connection() {
        // given

        // when

        // then
    }

    @Test
    fun check_if_get_remote_then_error_default() {
        // given

        // when

        // then
    }

    @Test
    fun check_if_get_local_then_error_cannot_get_movies() {
        // given

        // when

        // then
    }

    @Test
    fun check_if_get_local_then_error_cannot_insert_movies() {
        // given

        // when

        // then
    }




    @Test
    fun check_if_update_then_success() {
        // given

        // when

        // then
    }

    @Test
    fun check_if_update_then_error_cannot_update() {
        // given

        // when

        // then
    }

}