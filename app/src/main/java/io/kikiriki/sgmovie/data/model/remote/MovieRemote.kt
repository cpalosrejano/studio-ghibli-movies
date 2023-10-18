package io.kikiriki.sgmovie.data.model.remote

import io.kikiriki.sgmovie.data.model.domain.Movie

data class MovieRemote(
    val id: String? = null,
    val title: String? = null,
    val original_title_romanised: String? = null,
    val image: String? = null,
    val movie_banner: String? = null,
    val description: String,
    val director: String? = null,
    val producer: String? = null,
    val release_date: String,
    val running_time: String? = null,
    val rt_score: String,
)

fun MovieRemote.toDomain() = Movie(
    id = id.toString(),
    title = title.toString(),
    originalTitleRomanised = original_title_romanised.toString(),
    image = image.toString(),
    movieBanner = movie_banner,
    description = description,
    director = director.toString(),
    producer = producer,
    releaseDate = release_date.toInt(),
    runningTime = running_time,
    rtScore = rt_score.toInt()
)

fun List<MovieRemote>.toDomain() : List<Movie> {
    return this.map { it.toDomain() }
}