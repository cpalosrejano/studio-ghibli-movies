package io.kikiriki.sgmovie.framework.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.kikiriki.sgmovie.data.repository.note.NoteRepository
import io.kikiriki.sgmovie.data.repository.note.NoteRepositoryImpl
import io.kikiriki.sgmovie.data.repository.note.local.NoteLocalDataSource
import io.kikiriki.sgmovie.data.repository.note.mock.NoteMockDataSource
import io.kikiriki.sgmovie.data.repository.note.remote.NoteRemoteDataSource

@Module
@InstallIn(ActivityComponent::class)
abstract class NoteRepositoryModule {

    @Binds
    abstract fun bindNoteRemoteDataSource(implementation: NoteRemoteDataSource) : NoteRepository.RemoteDataSource

    @Binds
    abstract fun bindNoteLocalDataSource(implementation: NoteLocalDataSource) : NoteRepository.LocalDataSource

    @Binds
    abstract fun bindNoteMockDataSource(implementation: NoteMockDataSource) : NoteRepository.MockDataSource

    @Binds
    abstract fun bindNoteRepository(implementation: NoteRepositoryImpl) : NoteRepository
}