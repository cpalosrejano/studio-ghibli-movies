package io.kikiriki.sgmovie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val title: String,
    val originalTitleRomanised: String? = null,
    val image: String,
    val movieBanner: String? = null,
    val description: String,
    val director: String,
    val producer: String? = null,
    val releaseDate: Int,
    val runningTime: String? = null,
    val rtScore: Int,
    val favourite: Boolean = false
) : Parcelable
