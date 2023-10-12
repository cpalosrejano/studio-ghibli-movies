package io.kikiriki.sgmovie.data.model.local

import io.kikiriki.sgmovie.data.model.domain.Movie

data class MovieLocal(
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

fun MovieLocal.toDomain() = Movie(
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

fun List<MovieLocal>.toDomain() : List<Movie> {
    return this.map { it.toDomain() }
}