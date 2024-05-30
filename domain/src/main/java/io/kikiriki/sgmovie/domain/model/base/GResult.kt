package io.kikiriki.sgmovie.domain.model.base

sealed class GResult<out D, out E> {
    data class Success<out D>(val data: D) : GResult<D, Nothing>()
    data class Error<out E: Any>(val error: E) : GResult<Nothing, E>()
    data class SuccessWithError<out D: Any, out E: Any>(val data: D, val error: E) : GResult<D, E>()
}