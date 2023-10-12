package io.kikiriki.sgmovie.framework.hilt

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.kikiriki.sgmovie.data.repository.movie.local.MovieDao
import io.kikiriki.sgmovie.framework.room.AppDatabase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "sgmovie-database").build()
    }

    @Provides
    fun provideMovieDao(database: AppDatabase) : MovieDao {
        return database.movieDao()
    }

}