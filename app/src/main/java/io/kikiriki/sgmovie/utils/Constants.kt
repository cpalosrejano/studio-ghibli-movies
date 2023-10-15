package io.kikiriki.sgmovie.utils

import io.kikiriki.sgmovie.utils.extension.dp
import io.kikiriki.sgmovie.utils.extension.px

object Constants {

    // Data repository constants
    object Repository {
        const val MOCK = false
        const val URL = "https://ghibliapi.vercel.app/"
    }

    object Coil {
        const val CROSSFADE: Int = 600
        const val ROUNDED_CORNERS: Float = 16f
        val ROUNDED_CORNERS_XL: Float = 28.px.toFloat()
    }

}