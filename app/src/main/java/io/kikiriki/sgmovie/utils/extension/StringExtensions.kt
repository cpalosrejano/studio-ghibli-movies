package io.kikiriki.sgmovie.utils.extension

fun String.firstPhrase() : String {
    if (this.contains("\n")) {
        val slices = this.split("\n")
        return slices.getOrNull(0).orEmpty()
    } else {
        return this
    }
}
