package io.kikiriki.sgmovie.data.model.domain

import io.kikiriki.sgmovie.data.model.local.UserLocal
import io.kikiriki.sgmovie.data.model.remote.UserRemote
import io.kikiriki.sgmovie.utils.extension.format
import java.util.Date

data class User (
    val id: Long?,
    val name: String?,
    val email: String?,
    val birthDate: Date?,
    val avatar: String?
)

fun User.toRemote(): UserRemote {
    return UserRemote(
        id = id,
        name = name,
        email = email,
        birthDate = birthDate.format(),
        avatar = avatar
    )
}

fun User.toLocal(): UserLocal {
    return UserLocal(
        id = id,
        name = name,
        email = email,
        birthDate = birthDate.format(),
        avatar = avatar
    )
}