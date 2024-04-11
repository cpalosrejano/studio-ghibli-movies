package io.kikiriki.sgmovie.data.framework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import io.kikiriki.sgmovie.data.model.local.MovieLocal
import io.kikiriki.sgmovie.data.repository.movie.local.MovieDao

@Database(
    version = 1,
    entities = [MovieLocal::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}