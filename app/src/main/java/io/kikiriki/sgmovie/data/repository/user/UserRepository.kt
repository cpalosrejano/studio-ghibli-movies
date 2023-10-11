package io.kikiriki.sgmovie.data.repository.user

import io.kikiriki.sgmovie.data.model.domain.User
import io.kikiriki.sgmovie.data.model.local.UserLocal
import io.kikiriki.sgmovie.data.model.remote.UserRemote

interface UserRepository {

    suspend fun getUser(token: String) : Result<User>

    interface RemoteDataSource {
        suspend fun getUser(token: String) : Result<UserRemote>
    }

    interface LocalDataSource {
        suspend fun getUser() : Result<UserLocal>
    }

    interface MockDataSource : UserRepository
}