package io.kikiriki.sgmovie.utils

import io.kikiriki.sgmovie.R

class ExceptionManager {

    companion object {

        /**
         * Given a Exception return the @StringRes message
         */
        fun getMessage(throwable: Throwable) : Int {

            return R.string.default_error
            /*
            val code =
                (throwable as? RemoteDataSourceException)?.code ?:
                (throwable as? LocalDataSourceException)?.code

            return when (code) {

                RemoteDataSourceException.Code.UNAUTHORIZED -> R.string.error_network_unauthorized
                RemoteDataSourceException.Code.RESOURCE_NOT_FOUND -> R.string.error_network_not_found

                LocalDataSourceException.Code.CANNOT_GET_MOVIES -> R.string.error_bbdd_get_movies
                LocalDataSourceException.Code.CANNOT_UPDATE_MOVIE -> R.string.error_bbdd_update_movie
                LocalDataSourceException.Code.CANNOT_INSERT_MOVIES -> R.string.error_bbdd_insert_movies

                else -> {
                    when (throwable) {
                        is RemoteDataSourceException -> R.string.default_remote_error
                        is LocalDataSourceException -> R.string.default_local_error
                        else -> R.string.default_error
                    }
                }
            }
             */
        }
    }
}