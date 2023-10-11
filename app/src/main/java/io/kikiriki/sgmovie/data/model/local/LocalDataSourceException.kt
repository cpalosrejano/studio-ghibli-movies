package io.kikiriki.sgmovie.data.model.local

class LocalDataSourceException(
    val code: Int,
    message: String
) : Exception(message)