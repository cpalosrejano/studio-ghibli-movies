package io.kikiriki.sgmovie.utils

import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.data.model.local.LocalDataSourceException
import io.kikiriki.sgmovie.data.model.remote.RemoteDataSourceException
import io.kikiriki.sgmovie.utils.ExceptionManager.Code.Companion.NOT_IMPLEMENTED_YET

class ExceptionManager {

    class Code { companion object {
        const val NOT_IMPLEMENTED_YET = 0
    }}

    companion object {

        /**
         * Given a Exception return the @StringRes message
         */
        fun getMessage(exception: Exception) : Int {
            val code: Int? =
                (exception as? RemoteDataSourceException)?.code ?:
                (exception as? LocalDataSourceException)?.code

            return when (code) {

                NOT_IMPLEMENTED_YET -> R.string.error_feature_not_implemented

                else -> {
                    when (exception) {
                        is RemoteDataSourceException -> R.string.default_remote_error
                        is LocalDataSourceException -> R.string.default_local_error
                        else -> R.string.default_error
                    }
                }
            }
        }
    }
}