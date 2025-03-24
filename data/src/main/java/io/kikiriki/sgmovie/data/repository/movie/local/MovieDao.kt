package io.kikiriki.sgmovie.data.repository.movie.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.kikiriki.sgmovie.data.model.movie.MovieLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): Flow<List<MovieLocal>>

    @Query("SELECT * FROM movies where `like` == 1")
    suspend fun getMoviesLike(): List<MovieLocal>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: String): Flow<MovieLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<MovieLocal>)

    @Update()
    suspend fun updateMovieLike(movie: MovieLocal): Int

}