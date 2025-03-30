package io.kikiriki.sgmovie.utils.extension

import android.content.Context
import androidx.core.content.pm.PackageInfoCompat

fun Context.getVersionCode(): Long {
    return try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        PackageInfoCompat.getLongVersionCode(packageInfo)
    } catch (e: Exception) {
        0
    }
}