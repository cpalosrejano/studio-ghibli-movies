package io.kikiriki.sgmovie.data.framework.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSource
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSource
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieRemoteDataSource

@Module
@InstallIn(ActivityComponent::class)
abstract class MovieRepositoryModule {

    @Binds
    abstract fun bindMovieRemoteDataSource(implementation: MovieRemoteDataSource) : io.kikiriki.sgmovie.data.repository.movie.MovieRepository.RemoteDataSource

    @Binds
    abstract fun bindMovieLocalDataSource(implementation: MovieLocalDataSource) : io.kikiriki.sgmovie.data.repository.movie.MovieRepository.LocalDataSource

    @Binds
    abstract fun bindMovieMockDataSource(implementation: MovieMockDataSource) : io.kikiriki.sgmovie.data.repository.movie.MovieRepository.MockDataSource

    @Binds
    abstract fun bindMovieRepository(implementation: io.kikiriki.sgmovie.data.repository.movie.MovieRepositoryImpl) : io.kikiriki.sgmovie.data.repository.movie.MovieRepository
}