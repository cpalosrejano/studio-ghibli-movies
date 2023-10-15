package io.kikiriki.sgmovie.data.model.domain

import android.os.Parcelable
import io.kikiriki.sgmovie.data.model.local.MovieLocal
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val title: String,
    val originalTitleRomanised: String,
    val image: String,
    val movieBanner: String? = null,
    val description: String,
    val director: String,
    val producer: String? = null,
    val releaseDate: String? = null,
    val runningTime: String? = null,
    val rtScore: String,
    var favourite: Boolean = false
) : Parcelable

fun Movie.toLocal() = MovieLocal(
    id = id,
    title = title,
    original_title_romanised = originalTitleRomanised,
    image = image,
    movie_banner = movieBanner,
    description = description,
    director = director,
    producer = producer,
    release_date = releaseDate,
    running_time = runningTime,
    rt_score = rtScore
)

