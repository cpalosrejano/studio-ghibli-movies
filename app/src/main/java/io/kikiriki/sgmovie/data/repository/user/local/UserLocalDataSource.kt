package io.kikiriki.sgmovie.data.repository.user.local

import io.kikiriki.sgmovie.data.model.local.LocalDataSourceException
import io.kikiriki.sgmovie.data.model.local.UserLocal
import io.kikiriki.sgmovie.data.repository.user.UserRepository
import io.kikiriki.sgmovie.utils.ExceptionManager
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    // val room: UserDatabase
)  : UserRepository.LocalDataSource {

    override suspend fun getUser(): Result<UserLocal> {
        // TODO: feature not implemented yet
        return Result.failure(LocalDataSourceException(
            code = ExceptionManager.Code.NOT_IMPLEMENTED_YET,
            message = "Feature not implemented yet"
        ))
    }

}