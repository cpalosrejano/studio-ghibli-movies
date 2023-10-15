package io.kikiriki.sgmovie.utils.extension

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

fun <T: Parcelable> Bundle.getParcelableSupport(name: String, clazz: Class<T>) : T? {
    return if (Build.VERSION.SDK_INT >= 33) {
        this.getParcelable(name, clazz)
    } else {
        this.getParcelable(name) as? T
    }
}