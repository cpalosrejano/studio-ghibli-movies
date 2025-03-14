package io.kikiriki.sgmovie.utils.extension

import java.text.DecimalFormat

fun Long.formatNumber(): String {
    return when {
        this >= 1_000_000 -> DecimalFormat("#.#M").format(this / 1_000_000)
        this >= 1_000 -> DecimalFormat("#.#K").format(this / 1_000)
        else -> this.toString()
    }
}