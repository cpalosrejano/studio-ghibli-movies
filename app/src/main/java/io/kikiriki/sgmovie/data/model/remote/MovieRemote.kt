package io.kikiriki.sgmovie.data.model.remote

import io.kikiriki.sgmovie.data.model.domain.Movie

data class MovieRemote(
    val id: String? = null,
    val title: String? = null,
    val originalTitleRomanised: String? = null,
    val image: String? = null,
    val movieBanner: String? = null,
    val description: String,
    val director: String? = null,
    val producer: String? = null,
    val releaseDate: String? = null,
    val runningTime: String? = null,
    val rtScore: String? = null,
)

fun MovieRemote.toDomain() = Movie(
    id = id.toString(),
    title = title.toString(),
    originalTitleRomanised = originalTitleRomanised.toString(),
    image = image.toString(),
    movieBanner = movieBanner,
    description = description,
    director = director.toString(),
    producer = producer,
    releaseDate = releaseDate,
    runningTime = runningTime,
    rtScore = rtScore.toString()
)

fun List<MovieRemote>.toDomain() : List<Movie> {
    return this.map { it.toDomain() }
}