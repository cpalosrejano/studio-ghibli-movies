package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.data.local.dao.MovieDao
import io.kikiriki.sgmovie.data.model.domain.toLocal
import io.kikiriki.sgmovie.data.model.local.toDomain
import io.kikiriki.sgmovie.data.remote.movie.MovieEndpoints
import io.kikiriki.sgmovie.data.repository.LocalDataSourceException
import io.kikiriki.sgmovie.data.repository.RemoteDataSourceException
import io.kikiriki.sgmovie.util.DataMock
import io.kikiriki.sgmovie.utils.ExceptionManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class MovieRepositoryTest {

    @RelaxedMockK private lateinit var endpoints: io.kikiriki.sgmovie.data.remote.movie.MovieEndpoints
    @RelaxedMockK private lateinit var dao: io.kikiriki.sgmovie.data.local.dao.MovieDao

    private lateinit var repository: io.kikiriki.sgmovie.data.repository.movie.MovieRepository

    private val fields = "id,title,original_title_romanised,image,movie_banner,description,director,producer,release_date,running_time,rt_score"
    private val limit = 250

    @Before
    fun onStart() {
        MockKAnnotations.init(this)

        // build repository
        repository = io.kikiriki.sgmovie.data.repository.movie.MovieRepositoryImpl(
            remote = MovieRemoteDataSource(endpoints, Dispatchers.Unconfined),
            local = MovieLocalDataSource(dao, Dispatchers.Unconfined),
            mock = MovieMockDataSource(Dispatchers.Unconfined),
            dispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun check_if_get_remote_then_success() = runBlocking {
        // given
        coEvery { endpoints.getMovies(limit, fields) } returns DataMock.moviesRemote
        coEvery { dao.getAll() } returns flowOf(DataMock.moviesLocal)

        // when
        val movies = repository.get().first()

        // then
        assert( movies.isNotEmpty() )
    }

    @Test
    fun check_if_get_remote_then_error_unauthorized() = runBlocking {
        // given
        val httpException = HttpException(Response.error<ResponseBody>(401, "Un authorized".toResponseBody("plain/text".toMediaTypeOrNull())))
        coEvery { endpoints.getMovies(limit, fields) } throws httpException

        // when
        var exception: Exception? = null
        try {
            repository.get().first()
        } catch (e: Exception) {
            exception = e
        }

        // then
        assert( exception is io.kikiriki.sgmovie.data.repository.RemoteDataSourceException)
        assert( (exception as? io.kikiriki.sgmovie.data.repository.RemoteDataSourceException)?.code == ExceptionManager.Code.NETWORK_UNAUTHORIZED )
    }

    @Test
    fun check_if_get_remote_then_error_resource_not_found() = runBlocking {
        // given
        val httpException = HttpException(Response.error<ResponseBody>(404, "Resource not found".toResponseBody("plain/text".toMediaTypeOrNull())))
        coEvery { endpoints.getMovies(limit, fields) } throws httpException

        // when
        var exception: Exception? = null
        try {
            repository.get().first()
        } catch (e: Exception) {
            exception = e
        }

        // then
        assert( exception is io.kikiriki.sgmovie.data.repository.RemoteDataSourceException)
        assert( (exception as? io.kikiriki.sgmovie.data.repository.RemoteDataSourceException)?.code == ExceptionManager.Code.NETWORK_NOT_FOUND )

    }

    @Test
    fun check_if_get_remote_then_error_default() = runBlocking {
        // given
        val httpException = HttpException(Response.error<ResponseBody>(501, "Not implemented".toResponseBody("plain/text".toMediaTypeOrNull())))
        coEvery { endpoints.getMovies(limit, fields) } throws httpException

        // when
        var exception: Exception? = null
        try {
            repository.get().first()
        } catch (e: Exception) {
            exception = e
        }

        // then
        assert( exception is io.kikiriki.sgmovie.data.repository.RemoteDataSourceException)
        assert( (exception as? io.kikiriki.sgmovie.data.repository.RemoteDataSourceException)?.code == ExceptionManager.Code.DEFAULT_ERROR )

    }

    @Test
    fun check_if_get_local_then_error_cannot_get_movies() = runBlocking {
        // given
        val localException = io.kikiriki.sgmovie.data.repository.LocalDataSourceException(
            ExceptionManager.Code.BBDD_CANNOT_GET_MOVIES,
            "BBDD_CANNOT_GET_MOVIES"
        )
        coEvery { endpoints.getMovies(limit, fields) } returns DataMock.moviesRemote
        coEvery { dao.getAll() } throws localException

        // when
        var exception: Exception? = null
        try {
            repository.get().first()
        } catch (e: Exception) {
            exception = e
        }

        // then
        assert( exception is io.kikiriki.sgmovie.data.repository.LocalDataSourceException)
        assert( (exception as? io.kikiriki.sgmovie.data.repository.LocalDataSourceException)?.code == ExceptionManager.Code.BBDD_CANNOT_GET_MOVIES )

    }

    @Test
    fun check_if_get_local_then_error_cannot_insert_movies() = runBlocking {
        // given
        val localException = io.kikiriki.sgmovie.data.repository.LocalDataSourceException(
            ExceptionManager.Code.BBDD_CANNOT_INSERT_MOVIES,
            "BBDD_CANNOT_INSERT_MOVIES"
        )
        coEvery { endpoints.getMovies(limit, fields) } returns DataMock.moviesRemote
        coEvery { dao.getAll() } throws localException

        // when
        var exception: Exception? = null
        try {
            repository.get().first()
        } catch (e: Exception) {
            exception = e
        }

        // then
        assert( exception is io.kikiriki.sgmovie.data.repository.LocalDataSourceException)
        assert( (exception as? io.kikiriki.sgmovie.data.repository.LocalDataSourceException)?.code == ExceptionManager.Code.BBDD_CANNOT_INSERT_MOVIES )

    }

    @Test
    fun check_if_get_local_then_error_cannot_update_movies() = runBlocking {
        // given
        val localException = io.kikiriki.sgmovie.data.repository.LocalDataSourceException(
            ExceptionManager.Code.BBDD_CANNOT_UPDATE_MOVIE,
            "BBDD_CANNOT_UPDATE_MOVIE"
        )
        val movieLocal = DataMock.moviesLocal.first()
        coEvery { dao.updateFavourite(movieLocal) } throws localException

        // when
        val result = repository.update(DataMock.moviesLocal.first().toDomain())

        // then
        assert( result.isFailure )
        assert( result.exceptionOrNull() is io.kikiriki.sgmovie.data.repository.LocalDataSourceException)
        assert( (result.exceptionOrNull() as? io.kikiriki.sgmovie.data.repository.LocalDataSourceException)?.code == ExceptionManager.Code.BBDD_CANNOT_UPDATE_MOVIE )

    }

    @Test
    fun check_if_update_then_success() = runBlocking {
        // given
        val movie = DataMock.movies.first()
        coEvery { dao.updateFavourite(movie.toLocal()) } returns 1

        // when
        val result = repository.update(movie)

        // then
        assert( result.isSuccess )
        assert( result.getOrNull() is Boolean )
        assert( result.getOrNull() == true)
    }

}