package io.kikiriki.sgmovie.data.utils

class RemoteDataSourceException(
    val code: Code,
    message: String
) : Exception(message) {

    enum class Code {
        UNAUTHORIZED,
        RESOURCE_NOT_FOUND,
        HTTP_UNKNOWN,
        DEFAULT
    }

}