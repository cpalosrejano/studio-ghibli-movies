package io.kikiriki.sgmovie.data.model.remote

import io.kikiriki.sgmovie.data.model.domain.User
import io.kikiriki.sgmovie.utils.extension.toDate

data class UserRemote(
    val id: Long?,
    val name: String?,
    val email: String?,
    val birthDate: String?,
    val avatar: String?
)

fun UserRemote.toDomain() : User {
    return User(
        id = id,
        name = name,
        email = email,
        birthDate = birthDate.toDate(),
        avatar = avatar
    )
}