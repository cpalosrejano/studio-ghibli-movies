package io.kikiriki.sgmovie.utils

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

object CoilUtils {

    private lateinit var gifImageLoader: ImageLoader

    fun getImageLoaderGif(context: Context) : ImageLoader {
        if (! (CoilUtils::gifImageLoader.isInitialized)) {
            gifImageLoader = ImageLoader.Builder(context)
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }.build()
        }

        return gifImageLoader
    }
}

