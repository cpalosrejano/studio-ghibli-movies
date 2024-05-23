package io.kikiriki.sgmovie.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.kikiriki.sgmovie.data.repository.movie.MovieLocalDataSource
import io.kikiriki.sgmovie.data.repository.movie.MovieMockDataSource
import io.kikiriki.sgmovie.data.repository.movie.MovieRemoteDataSource
import io.kikiriki.sgmovie.data.repository.movie.MovieRepositoryImpl
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieRemoteDataSourceImpl
import io.kikiriki.sgmovie.domain.repository.MovieRepository

@Module
@InstallIn(ActivityComponent::class)
abstract class MovieRepositoryModule {

    @Binds
    abstract fun bindMovieRemoteDataSource(implementation: MovieRemoteDataSourceImpl) : MovieRemoteDataSource

    @Binds
    abstract fun bindMovieLocalDataSource(implementation: MovieLocalDataSourceImpl) : MovieLocalDataSource

    @Binds
    abstract fun bindMovieMockDataSource(implementation: MovieMockDataSourceImpl) : MovieMockDataSource

    @Binds
    abstract fun bindMovieRepository(implementation: MovieRepositoryImpl) : MovieRepository
}