package io.kikiriki.sgmovie.data.model.domain

import io.kikiriki.sgmovie.data.model.local.MovieLocal

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
    val rtScore: String
)

fun Movie.toLocal() = MovieLocal(
    id = id,
    title = title,
    originalTitleRomanised = originalTitleRomanised,
    image = image,
    movieBanner = movieBanner,
    description = description,
    director = director,
    producer = producer,
    releaseDate = releaseDate,
    runningTime = runningTime,
    rtScore = rtScore
)

