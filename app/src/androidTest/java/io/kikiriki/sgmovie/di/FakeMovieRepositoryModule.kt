package io.kikiriki.sgmovie.di

import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.testing.TestInstallIn
import io.kikiriki.sgmovie.data.di.MovieRepositoryModule
import io.kikiriki.sgmovie.data.repository.movie.MovieRepositoryImpl
import io.kikiriki.sgmovie.data.repository.movie.firestore.MovieFirestoreDataSource
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSource
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSource
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieRemoteDataSource
import io.kikiriki.sgmovie.domain.repository.MovieRepository

/**
 * This class will replace the MovieRepositoryModule with its own dependencies.
 * In our case, we will "re-implement" the RemoteDataSource to avoid make API query while we are
 * testing our app. The rest of DataSources (database in this case) will remain as always.
 */

@TestInstallIn(
    components = [ActivityComponent::class],
    replaces = [MovieRepositoryModule::class]
)
@Module
abstract class FakeMovieRepositoryModule {

    @Binds
    abstract fun bindMovieFirestoreDataSource(implementation: FakeMovieFirestoreDataSourceImpl) : MovieFirestoreDataSource

    @Binds
    abstract fun bindMovieRemoteDataSource(implementation: FakeMovieRemoteDataSourceImpl) : MovieRemoteDataSource

    @Binds
    abstract fun bindMovieLocalDataSource(implementation: MovieLocalDataSourceImpl) : MovieLocalDataSource

    @Binds
    abstract fun bindMovieMockDataSource(implementation: MovieMockDataSourceImpl) : MovieMockDataSource

    @Binds
    abstract fun bindMovieRepository(implementation: MovieRepositoryImpl) : MovieRepository

}