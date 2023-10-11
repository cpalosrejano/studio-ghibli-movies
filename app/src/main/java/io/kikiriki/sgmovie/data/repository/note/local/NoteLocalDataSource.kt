package io.kikiriki.sgmovie.data.repository.note.local

import io.kikiriki.sgmovie.data.model.local.LocalDataSourceException
import io.kikiriki.sgmovie.data.model.local.NoteLocal
import io.kikiriki.sgmovie.data.repository.note.NoteRepository
import io.kikiriki.sgmovie.utils.ExceptionManager
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor(
    // val room: NoteDatabase
)  : NoteRepository.LocalDataSource {

    override suspend fun add(note: NoteLocal): Result<NoteLocal> {
        // TODO: feature not implemented yet
        return Result.failure(LocalDataSourceException(
            code = ExceptionManager.Code.NOT_IMPLEMENTED_YET,
            message = "Feature not implemented yet"
        ))
    }

    override suspend fun update(note: NoteLocal): Result<NoteLocal> {
        // TODO: feature not implemented yet
        return Result.failure(LocalDataSourceException(
            code = ExceptionManager.Code.NOT_IMPLEMENTED_YET,
            message = "Feature not implemented yet"
        ))
    }

    override suspend fun delete(note: NoteLocal): Result<NoteLocal> {
        // TODO: feature not implemented yet
        return Result.failure(LocalDataSourceException(
            code = ExceptionManager.Code.NOT_IMPLEMENTED_YET,
            message = "Feature not implemented yet"
        ))
    }

    override suspend fun get(id: Long): Result<NoteLocal> {
        // TODO: feature not implemented yet
        return Result.failure(LocalDataSourceException(
            code = ExceptionManager.Code.NOT_IMPLEMENTED_YET,
            message = "Feature not implemented yet"
        ))
    }

    override suspend fun getAll(): Result<List<NoteLocal>> {
        // TODO: feature not implemented yet
        return Result.failure(LocalDataSourceException(
            code = ExceptionManager.Code.NOT_IMPLEMENTED_YET,
            message = "Feature not implemented yet"
        ))
    }

}