package io.kikiriki.sgmovie.domain.user

import io.kikiriki.sgmovie.data.model.domain.User
import io.kikiriki.sgmovie.data.repository.user.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() : Result<User> {
        return userRepository.getUser("null")
    }

}