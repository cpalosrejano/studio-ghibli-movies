package io.kikiriki.sgmovie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val title: String,
    val titleRomanised: String,
    val imageCartel: String,
    val imageBanner: String,
    val description: String,
    val director: String,
    val producer: String,
    val soundtrack: String,
    val releaseDate: Int,
    val runningTime: Int,
    val rtScore: Int,
    val coproduction: Boolean,
    val like: Boolean = false,
    val likeCount: Long = 0
) : Parcelable
