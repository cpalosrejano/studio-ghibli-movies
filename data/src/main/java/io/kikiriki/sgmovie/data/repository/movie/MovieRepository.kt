package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.data.model.local.MovieLocal
import io.kikiriki.sgmovie.data.model.remote.MovieRemote
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun get() : Flow<List<io.kikiriki.sgmovie.data.model.MovieRepository>>

    //fun refreshMovies() : Flow<List<io.kikiriki.sgmovie.data.model.MovieRepository>>
    suspend fun update(movie: io.kikiriki.sgmovie.data.model.MovieRepository) : Result<Boolean>

    interface RemoteDataSource {
        suspend fun get() : Result<List<MovieRemote>>
    }

    interface LocalDataSource {
        fun get() : Flow<List<MovieLocal>>
        /** Insert or update the current movies. This insert respect the value of previously favourites movies*/
        suspend fun insert(movies: List<MovieLocal>) : Result<Boolean>
        suspend fun update(movie: MovieLocal) : Result<Boolean>
    }

    interface MockDataSource : MovieRepository

}