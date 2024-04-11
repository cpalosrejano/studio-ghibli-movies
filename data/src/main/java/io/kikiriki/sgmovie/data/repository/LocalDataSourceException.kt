package io.kikiriki.sgmovie.data.repository

class LocalDataSourceException(
    val code: Code,
    message: String
) : Exception(message) {

    enum class Code {
        CANNOT_GET_MOVIES,
        CANNOT_INSERT_MOVIES,
        CANNOT_UPDATE_MOVIE,
        DEFAULT
    }

}