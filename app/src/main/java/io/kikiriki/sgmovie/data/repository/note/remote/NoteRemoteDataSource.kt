package io.kikiriki.sgmovie.data.repository.note.remote

import io.kikiriki.sgmovie.data.model.remote.NoteRemote
import io.kikiriki.sgmovie.data.model.remote.RemoteDataSourceException
import io.kikiriki.sgmovie.data.repository.note.NoteRepository
import io.kikiriki.sgmovie.utils.ExceptionManager
import javax.inject.Inject

class NoteRemoteDataSource @Inject constructor(
    // val endpoints: AuthEndpoints
    // val firebaseDatabase: Firebase.database
)  : NoteRepository.RemoteDataSource {

    override suspend fun add(note: NoteRemote): Result<NoteRemote> {
        /*
            TODO: not implemented yet
             check if our servers returns an http code
             and add to ExceptionManager.Code
         */
        return Result.failure(RemoteDataSourceException(
            code = ExceptionManager.Code.NOT_IMPLEMENTED_YET,
            message = "Feature not implemented yet",
            httpCode = 404
        ))
    }

    override suspend fun update(note: NoteRemote): Result<NoteRemote> {
        /*
            TODO: not implemented yet
             check if our servers returns an http code
             and add to ExceptionManager.Code
         */
        return Result.failure(RemoteDataSourceException(
            code = ExceptionManager.Code.NOT_IMPLEMENTED_YET,
            message = "Feature not implemented yet",
            httpCode = 404
        ))
    }

    override suspend fun delete(note: NoteRemote): Result<NoteRemote> {
        /*
            TODO: not implemented yet
             check if our servers returns an http code
             and add to ExceptionManager.Code
         */
        return Result.failure(RemoteDataSourceException(
            code = ExceptionManager.Code.NOT_IMPLEMENTED_YET,
            message = "Feature not implemented yet",
            httpCode = 404
        ))
    }

    override suspend fun get(id: Long): Result<NoteRemote> {
        /*
            TODO: not implemented yet
             check if our servers returns an http code
             and add to ExceptionManager.Code
         */
        return Result.failure(RemoteDataSourceException(
            code = ExceptionManager.Code.NOT_IMPLEMENTED_YET,
            message = "Feature not implemented yet",
            httpCode = 404
        ))
    }

    override suspend fun getAll(): Result<List<NoteRemote>> {
        /*
            TODO: not implemented yet
             check if our servers returns an http code
             and add to ExceptionManager.Code
         */        return Result.failure(RemoteDataSourceException(
            code = ExceptionManager.Code.NOT_IMPLEMENTED_YET,
            message = "Feature not implemented yet",
            httpCode = 404
        ))
    }

}