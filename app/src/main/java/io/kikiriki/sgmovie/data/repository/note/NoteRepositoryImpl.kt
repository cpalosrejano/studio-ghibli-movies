package io.kikiriki.sgmovie.data.repository.note

import io.kikiriki.sgmovie.data.model.domain.Note
import io.kikiriki.sgmovie.data.model.domain.toRemote
import io.kikiriki.sgmovie.data.model.remote.toDomain
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val remote: NoteRepository.RemoteDataSource,
    private val local: NoteRepository.LocalDataSource,
    private val mock: NoteRepository.MockDataSource
) : NoteRepository {

    override suspend fun create(note: Note): Result<Note> {
        return try {
            val remoteResult = remote.add(note.toRemote())
            val remoteData = remoteResult.getOrThrow()
            val data = remoteData.toDomain()
            Result.success(data)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

    override suspend fun update(note: Note): Result<Note> {
        return try {
            val remoteResult = remote.update(note.toRemote())
            val remoteData = remoteResult.getOrThrow()
            val data = remoteData.toDomain()
            Result.success(data)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

    override suspend fun delete(note: Note): Result<Note> {
        return try {
            val remoteResult = remote.delete(note.toRemote())
            val remoteData = remoteResult.getOrThrow()
            val data = remoteData.toDomain()
            Result.success(data)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

    override suspend fun get(id: Long): Result<Note> {
        return try {
            val remoteResult = remote.get(id)
            val remoteData = remoteResult.getOrThrow()
            val data = remoteData.toDomain()
            Result.success(data)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

    override suspend fun getAll(): Result<List<Note>> {
        return try {
            val remoteResult = remote.getAll()
            val remoteData = remoteResult.getOrThrow()
            val data = remoteData.toDomain()
            Result.success(data)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

}