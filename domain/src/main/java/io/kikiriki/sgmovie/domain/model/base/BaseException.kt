package io.kikiriki.sgmovie.domain.model.base

open class BaseException(
    val code: BaseCode,
    message: String,
) : Exception(message)

enum class BaseCode {
    REMOTE_UNAUTHORIZED,
    REMOTE_RESOURCE_NOT_FOUND,
    REMOTE_HTTP_UNKNOWN,
    REMOTE_NO_INTERNET_CONNECTION,

    LOCAL_CANNOT_GET_MOVIES,
    LOCAL_CANNOT_INSERT_MOVIES,
    LOCAL_CANNOT_UPDATE_MOVIE,

    DEFAULT
}