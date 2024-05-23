package io.kikiriki.sgmovie.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.kikiriki.sgmovie.data.AppDatabase
import io.kikiriki.sgmovie.data.repository.movie.local.MovieDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private lateinit var database: AppDatabase

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        if (! this::database.isInitialized) {
            database = Room.databaseBuilder(context, AppDatabase::class.java, "sgmovie-database").build()
        }
        return database
    }

    @Provides
    fun provideMovieDao(database: AppDatabase) : MovieDao {
        return database.movieDao()
    }

}