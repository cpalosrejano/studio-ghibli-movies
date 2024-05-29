package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.core.test.BaseTest
import io.kikiriki.sgmovie.data.exception.LocalDataSourceException
import io.kikiriki.sgmovie.data.exception.RemoteDataSourceException
import io.kikiriki.sgmovie.data.model.mapper.MovieMapper
import io.kikiriki.sgmovie.data.repository.movie.local.MovieDao
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieEndpoints
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieRemoteDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.util.DataMock
import io.kikiriki.sgmovie.domain.exception.BaseCode
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryTest : BaseTest() {

    @RelaxedMockK private lateinit var endpoints: MovieEndpoints
    @RelaxedMockK private lateinit var dao: MovieDao

    private lateinit var repository: MovieRepository

    private val fields = "id,title,original_title_romanised,image,movie_banner,description,director,producer,release_date,running_time,rt_score"
    private val limit = 250

    override fun onStart() {
        super.onStart()

        // build repository
        repository = MovieRepositoryImpl(
            remote = MovieRemoteDataSourceImpl(endpoints, Dispatchers.Unconfined),
            local = MovieLocalDataSourceImpl(dao, Dispatchers.Unconfined),
            mock = MovieMockDataSourceImpl(Dispatchers.Unconfined),
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
        assert( exception is RemoteDataSourceException)
        assert( (exception as? RemoteDataSourceException)?.code == BaseCode.REMOTE_UNAUTHORIZED )
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
        assert( exception is RemoteDataSourceException)
        assert( (exception as? RemoteDataSourceException)?.code == BaseCode.REMOTE_RESOURCE_NOT_FOUND )

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
        assert( exception is RemoteDataSourceException)
        assert( (exception as? RemoteDataSourceException)?.code == BaseCode.REMOTE_HTTP_UNKNOWN )

    }

    @Test
    fun check_if_get_local_then_error_cannot_get_movies() = runBlocking {
        // given
        val localException = LocalDataSourceException(
            LocalDataSourceException.Code.CANNOT_GET_MOVIES,
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
        assert( exception is LocalDataSourceException)
        assert( (exception as? LocalDataSourceException)?.code == BaseCode.LOCAL_CANNOT_GET_MOVIES )

    }

    @Test
    fun check_if_get_local_then_error_cannot_insert_movies() = runBlocking {
        // given
        val localException = LocalDataSourceException(
            LocalDataSourceException.Code.CANNOT_INSERT_MOVIES,
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
        assert( exception is LocalDataSourceException)
        assert( (exception as? LocalDataSourceException)?.code == BaseCode.LOCAL_CANNOT_INSERT_MOVIES )

    }

    @Test
    fun check_if_get_local_then_error_cannot_update_movies() = runBlocking {
        // given
        val localException = LocalDataSourceException(
            LocalDataSourceException.Code.CANNOT_UPDATE_MOVIE,
            "BBDD_CANNOT_UPDATE_MOVIE"
        )
        val movieLocal = DataMock.moviesLocal.first()
        coEvery { dao.updateFavourite(movieLocal) } throws localException

        // when
        val domainMovie = MovieMapper.localToData(DataMock.moviesLocal.first())
        val result = repository.update(domainMovie)

        // then
        assert( result.isFailure )
        assert( result.exceptionOrNull() is LocalDataSourceException)
        assert( (result.exceptionOrNull() as? LocalDataSourceException)?.code == BaseCode.LOCAL_CANNOT_UPDATE_MOVIE )

    }

    @Test
    fun check_if_update_then_success() = runBlocking {
        // given
        val movie = DataMock.movies.first()
        val localMovie = MovieMapper.dataToLocal(movie)
        coEvery { dao.updateFavourite(localMovie) } returns 1

        // when
        val result = repository.update(movie)

        // then
        assert( result.isSuccess )
        assert( result.getOrNull() is Boolean )
        assert( result.getOrNull() == true)
    }

}