package io.kikiriki.sgmovie.utils

import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.domain.exception.BaseCode
import io.kikiriki.sgmovie.domain.exception.BaseException

class ExceptionManager {

    companion object {

        /**
         * Given a Exception return the @StringRes message
         */
        fun getMessage(throwable: Throwable) : Int {

            val code: BaseCode? = (throwable as? BaseException)?.code

            return when (code) {

                BaseCode.REMOTE_UNAUTHORIZED -> R.string.error_network_unauthorized
                BaseCode.REMOTE_RESOURCE_NOT_FOUND -> R.string.error_network_not_found
                BaseCode.REMOTE_HTTP_UNKNOWN -> R.string.error_network_unknown
                BaseCode.REMOTE_NO_INTERNET_CONNECTION -> R.string.error_network_no_connected

                BaseCode.LOCAL_CANNOT_GET_MOVIES -> R.string.error_bbdd_get_movies
                BaseCode.LOCAL_CANNOT_UPDATE_MOVIE -> R.string.error_bbdd_update_movie
                BaseCode.LOCAL_CANNOT_INSERT_MOVIES -> R.string.error_bbdd_insert_movies

                else ->  R.string.default_error
            }

        }
    }
}