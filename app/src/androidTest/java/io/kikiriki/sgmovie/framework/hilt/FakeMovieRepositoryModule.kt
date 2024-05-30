package io.kikiriki.sgmovie.framework.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.testing.TestInstallIn
import io.kikiriki.sgmovie.data.di.MovieRepositoryModule
import io.kikiriki.sgmovie.data.repository.movie.FakeMovieRemoteDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.MovieLocalDataSource
import io.kikiriki.sgmovie.data.repository.movie.MovieMockDataSource
import io.kikiriki.sgmovie.data.repository.movie.MovieRemoteDataSource
import io.kikiriki.sgmovie.data.repository.movie.MovieRepositoryImpl
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSourceImpl
import io.kikiriki.sgmovie.domain.repository.MovieRepository


@TestInstallIn(
    components = [ActivityComponent::class],
    replaces = [MovieRepositoryModule::class]
)
@Module
abstract class FakeMovieRepositoryModule {

    @Binds
    abstract fun bindMovieRemoteDataSource(implementation: FakeMovieRemoteDataSourceImpl) : MovieRemoteDataSource

    @Binds
    abstract fun bindMovieLocalDataSource(implementation: MovieLocalDataSourceImpl) : MovieLocalDataSource

    @Binds
    abstract fun bindMovieMockDataSource(implementation: MovieMockDataSourceImpl) : MovieMockDataSource

    @Binds
    abstract fun bindMovieRepository(implementation: MovieRepositoryImpl) : MovieRepository

}