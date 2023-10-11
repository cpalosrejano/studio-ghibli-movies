package io.kikiriki.sgmovie.data.repository.note

import io.kikiriki.sgmovie.data.model.domain.Note
import io.kikiriki.sgmovie.data.model.local.NoteLocal
import io.kikiriki.sgmovie.data.model.remote.NoteRemote

interface NoteRepository {

    suspend fun create(note: Note) : Result<Note>
    suspend fun update(note: Note) : Result<Note>
    suspend fun delete(note: Note) : Result<Note>
    suspend fun get(id: Long) : Result<Note>
    suspend fun getAll() : Result<List<Note>>

    interface RemoteDataSource {
        suspend fun add(note: NoteRemote) : Result<NoteRemote>
        suspend fun update(note: NoteRemote) : Result<NoteRemote>
        suspend fun delete(note: NoteRemote) : Result<NoteRemote>
        suspend fun get(id: Long) : Result<NoteRemote>
        suspend fun getAll() : Result<List<NoteRemote>>
    }

    interface LocalDataSource {
        suspend fun add(note: NoteLocal) : Result<NoteLocal>
        suspend fun update(note: NoteLocal) : Result<NoteLocal>
        suspend fun delete(note: NoteLocal) : Result<NoteLocal>
        suspend fun get(id: Long) : Result<NoteLocal>
        suspend fun getAll() : Result<List<NoteLocal>>
    }

    interface MockDataSource : NoteRepository

}