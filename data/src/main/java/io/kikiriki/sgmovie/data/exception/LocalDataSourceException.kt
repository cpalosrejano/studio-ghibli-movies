package io.kikiriki.sgmovie.data.exception

import io.kikiriki.sgmovie.domain.model.base.BaseCode
import io.kikiriki.sgmovie.domain.model.base.BaseException

class LocalDataSourceException(
    code: Code,
    message: String
) : BaseException(code.code, message) {

    enum class Code (val code: BaseCode) {
        CANNOT_GET_MOVIES(BaseCode.LOCAL_CANNOT_GET_MOVIES),
        CANNOT_GET_MOVIE_DETAIL(BaseCode.LOCAL_CANNOT_GET_MOVIE_DETAIL),
        CANNOT_INSERT_MOVIES(BaseCode.LOCAL_CANNOT_INSERT_MOVIES),
        CANNOT_UPDATE_MOVIE(BaseCode.LOCAL_CANNOT_UPDATE_MOVIE),
    }
    
}