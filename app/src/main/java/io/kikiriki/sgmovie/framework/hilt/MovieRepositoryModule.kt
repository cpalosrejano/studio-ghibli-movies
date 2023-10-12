package io.kikiriki.sgmovie.framework.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.kikiriki.sgmovie.data.repository.movie.MovieRepository
import io.kikiriki.sgmovie.data.repository.movie.MovieRepositoryImpl
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSource
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSource
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieRemoteDataSource

@Module
@InstallIn(ActivityComponent::class)
abstract class MovieRepositoryModule {

    @Binds
    abstract fun bindMovieRemoteDataSource(implementation: MovieRemoteDataSource) : MovieRepository.RemoteDataSource

    @Binds
    abstract fun bindMovieLocalDataSource(implementation: MovieLocalDataSource) : MovieRepository.LocalDataSource

    @Binds
    abstract fun bindMovieMockDataSource(implementation: MovieMockDataSource) : MovieRepository.MockDataSource

    @Binds
    abstract fun bindMovieRepository(implementation: MovieRepositoryImpl) : MovieRepository
}