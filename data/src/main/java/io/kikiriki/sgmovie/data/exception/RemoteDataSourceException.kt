package io.kikiriki.sgmovie.data.exception

import io.kikiriki.sgmovie.domain.model.base.BaseCode
import io.kikiriki.sgmovie.domain.model.base.BaseException

class RemoteDataSourceException(
    code: Code,
    message: String
) : BaseException(code.code, message) {

    enum class Code (val code: BaseCode) {
        UNAUTHORIZED(BaseCode.REMOTE_UNAUTHORIZED),
        RESOURCE_NOT_FOUND(BaseCode.REMOTE_RESOURCE_NOT_FOUND),
        HTTP_UNKNOWN(BaseCode.REMOTE_HTTP_UNKNOWN),
        NO_INTERNET_CONNECTION(BaseCode.REMOTE_NO_INTERNET_CONNECTION),
        DEFAULT(BaseCode.DEFAULT)
    }

}