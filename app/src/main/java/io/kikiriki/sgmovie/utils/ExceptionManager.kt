package io.kikiriki.sgmovie.utils

import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.data.model.local.LocalDataSourceException
import io.kikiriki.sgmovie.data.model.remote.RemoteDataSourceException
import io.kikiriki.sgmovie.utils.ExceptionManager.Code.Companion.BBDD_CANNOT_GET_MOVIES
import io.kikiriki.sgmovie.utils.ExceptionManager.Code.Companion.BBDD_CANNOT_INSERT_MOVIES
import io.kikiriki.sgmovie.utils.ExceptionManager.Code.Companion.BBDD_CANNOT_UPDATE_MOVIE
import io.kikiriki.sgmovie.utils.ExceptionManager.Code.Companion.NETWORK_NOT_FOUND
import io.kikiriki.sgmovie.utils.ExceptionManager.Code.Companion.NETWORK_UNAUTHORIZED
import io.kikiriki.sgmovie.utils.ExceptionManager.Code.Companion.NOT_IMPLEMENTED_YET

class ExceptionManager {

    class Code { companion object {
        const val NOT_IMPLEMENTED_YET = -1
        const val DEFAULT_ERROR = 0

        const val NETWORK_UNAUTHORIZED = 1
        const val NETWORK_NOT_FOUND = 2

        const val BBDD_CANNOT_GET_MOVIES = 5
        const val BBDD_CANNOT_INSERT_MOVIES = 3
        const val BBDD_CANNOT_UPDATE_MOVIE = 4
    }}

    companion object {

        /**
         * Given a Exception return the @StringRes message
         */
        fun getMessage(throwable: Throwable) : Int {
            val code: Int? =
                (throwable as? RemoteDataSourceException)?.code ?:
                (throwable as? LocalDataSourceException)?.code

            return when (code) {

                // TODO: declare new error strings for each Code declared above
                NOT_IMPLEMENTED_YET -> R.string.error_feature_not_implemented

                NETWORK_UNAUTHORIZED -> R.string.error_network_unauthorized
                NETWORK_NOT_FOUND -> R.string.error_network_not_found

                BBDD_CANNOT_GET_MOVIES -> R.string.error_bbdd_get_movies
                BBDD_CANNOT_UPDATE_MOVIE -> R.string.error_bbdd_update_movie
                BBDD_CANNOT_INSERT_MOVIES -> R.string.error_bbdd_insert_movies

                else -> {
                    when (throwable) {
                        is RemoteDataSourceException -> R.string.default_remote_error
                        is LocalDataSourceException -> R.string.default_local_error
                        else -> R.string.default_error
                    }
                }
            }
        }
    }
}