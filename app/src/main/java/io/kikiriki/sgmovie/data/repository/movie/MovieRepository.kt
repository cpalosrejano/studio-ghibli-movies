package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.data.model.local.MovieLocal
import io.kikiriki.sgmovie.data.model.remote.MovieRemote

interface MovieRepository {

    suspend fun getAll() : Result<List<Movie>>
    suspend fun getDetail(movieId: String) : Result<Movie>

    suspend fun getFavorites() : Result<List<Movie>>
    suspend fun addFavorite(movie: Movie) : Result<Boolean>
    suspend fun deleteFavorite(movie: Movie) : Result<Boolean>

    interface RemoteDataSource {
        suspend fun getAll() : Result<List<MovieRemote>>
        suspend fun getDetail(movieId: String) : Result<MovieRemote>
    }

    interface LocalDataSource {
        suspend fun getFavorites() : Result<List<MovieLocal>>
        suspend fun addFavorite(movie: MovieLocal) : Result<Boolean>
        suspend fun deleteFavorite(movie: MovieLocal) : Result<Boolean>
    }

    interface MockDataSource : MovieRepository

}