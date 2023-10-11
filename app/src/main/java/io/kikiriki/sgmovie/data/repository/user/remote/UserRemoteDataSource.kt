package io.kikiriki.sgmovie.data.repository.user.remote

import io.kikiriki.sgmovie.data.model.remote.RemoteDataSourceException
import io.kikiriki.sgmovie.data.model.remote.UserRemote
import io.kikiriki.sgmovie.data.repository.user.UserRepository
import io.kikiriki.sgmovie.utils.ExceptionManager
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    // val endpoints: UserEndpoints
    // val firebaseDatabase: Firebase.database
)  : UserRepository.RemoteDataSource {

    override suspend fun getUser(token: String): Result<UserRemote> {
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

}