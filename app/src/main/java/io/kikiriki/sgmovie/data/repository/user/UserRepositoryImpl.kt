package io.kikiriki.sgmovie.data.repository.user

import io.kikiriki.sgmovie.data.model.domain.User
import io.kikiriki.sgmovie.data.model.local.toDomain
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remote: UserRepository.RemoteDataSource,
    private val local: UserRepository.LocalDataSource,
    private val mock: UserRepository.MockDataSource
) : UserRepository {

    override suspend fun getUser(token: String): Result<User> {
        return try {
            val remoteResult = local.getUser()
            val remoteData = remoteResult.getOrThrow()
            val data = remoteData.toDomain()
            Result.success(data)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }
}