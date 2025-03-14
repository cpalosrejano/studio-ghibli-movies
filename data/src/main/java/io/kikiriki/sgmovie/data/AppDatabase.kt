package io.kikiriki.sgmovie.data

import androidx.room.Database
import androidx.room.RoomDatabase
import io.kikiriki.sgmovie.data.model.MovieLocal
import io.kikiriki.sgmovie.data.repository.movie.local.MovieDao

@Database(
    version = 2,
    entities = [MovieLocal::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}