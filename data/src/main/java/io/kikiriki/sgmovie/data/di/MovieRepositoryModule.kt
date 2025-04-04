package io.kikiriki.sgmovie.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.kikiriki.sgmovie.data.repository.movie.MovieRepositoryImpl
import io.kikiriki.sgmovie.data.repository.movie.firestore.MovieFirestoreDataSource
import io.kikiriki.sgmovie.data.repository.movie.firestore.MovieFirestoreDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSource
import io.kikiriki.sgmovie.data.repository.movie.local.MovieLocalDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSource
import io.kikiriki.sgmovie.data.repository.movie.mock.MovieMockDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieRemoteDataSource
import io.kikiriki.sgmovie.data.repository.movie.remote.vercel.MovieRemoteDataSourceVercelImpl
import io.kikiriki.sgmovie.domain.repository.MovieRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepositoryModule {

    @Binds
    abstract fun bindMovieFirestoreDataSource(implementation: MovieFirestoreDataSourceImpl) : MovieFirestoreDataSource

    @Binds
    abstract fun bindMovieRemoteVercelDataSource(implementation: MovieRemoteDataSourceVercelImpl) : MovieRemoteDataSource

    @Binds
    abstract fun bindMovieLocalDataSource(implementation: MovieLocalDataSourceImpl) : MovieLocalDataSource

    @Binds
    abstract fun bindMovieMockDataSource(implementation: MovieMockDataSourceImpl) : MovieMockDataSource

    @Binds
    abstract fun bindMovieRepository(implementation: MovieRepositoryImpl) : MovieRepository
}