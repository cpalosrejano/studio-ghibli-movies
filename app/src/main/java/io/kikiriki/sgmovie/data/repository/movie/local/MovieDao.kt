package io.kikiriki.sgmovie.data.repository.movie.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.kikiriki.sgmovie.data.model.local.MovieLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieLocal")
    suspend fun getFavorites(): List<MovieLocal>

    @Query("SELECT * FROM MovieLocal")
    fun getFavoritesFlow(): Flow<List<MovieLocal>>

    @Insert
    suspend fun addFavorite(movie: MovieLocal)

    @Delete
    suspend fun deleteFavorite(movie: MovieLocal) : Int

}