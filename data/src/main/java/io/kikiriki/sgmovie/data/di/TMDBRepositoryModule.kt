package io.kikiriki.sgmovie.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.kikiriki.sgmovie.data.repository.tmdb.TMDBRepositoryImpl
import io.kikiriki.sgmovie.data.repository.tmdb.remote.TMDBRemoteDataSource
import io.kikiriki.sgmovie.data.repository.tmdb.remote.TMDBRemoteDataSourceImpl
import io.kikiriki.sgmovie.domain.repository.TMDBRepository

@Module
@InstallIn(ActivityComponent::class)
abstract class TMDBRepositoryModule {

    @Binds
    abstract fun bindTMDBRemoteDataSource(implementation: TMDBRemoteDataSourceImpl) : TMDBRemoteDataSource

    @Binds
    abstract fun bindTMDBRepository(implementation: TMDBRepositoryImpl) : TMDBRepository
}