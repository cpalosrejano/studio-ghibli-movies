package io.kikiriki.sgmovie.data.repository.movie.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.kikiriki.sgmovie.data.model.MovieLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieLocal")
    fun getAll(): Flow<List<MovieLocal>>

    @Query("SELECT * FROM MovieLocal where favourite == 1")
    suspend fun getFavourites(): List<MovieLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<MovieLocal>)

    @Update()
    suspend fun updateFavourite(movie: MovieLocal): Int

}