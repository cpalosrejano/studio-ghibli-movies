package io.kikiriki.sgmovie.framework.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.kikiriki.sgmovie.data.repository.user.UserRepository
import io.kikiriki.sgmovie.data.repository.user.UserRepositoryImpl
import io.kikiriki.sgmovie.data.repository.user.local.UserLocalDataSource
import io.kikiriki.sgmovie.data.repository.user.mock.UserMockDataSource
import io.kikiriki.sgmovie.data.repository.user.remote.UserRemoteDataSource

@Module
@InstallIn(ActivityComponent::class)
abstract class UserRepositoryModule {

    @Binds
    abstract fun bindNoteRemoteDataSource(implementation: UserRemoteDataSource) : UserRepository.RemoteDataSource

    @Binds
    abstract fun bindNoteLocalDataSource(implementation: UserLocalDataSource) : UserRepository.LocalDataSource

    @Binds
    abstract fun bindNoteMockDataSource(implementation: UserMockDataSource) : UserRepository.MockDataSource

    @Binds
    abstract fun bindNoteRepository(implementation: UserRepositoryImpl) : UserRepository
}