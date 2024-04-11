package io.kikiriki.sgmovie.data.model

import androidx.room.PrimaryKey
import io.kikiriki.sgmovie.data.model.local.MovieLocal
import io.kikiriki.sgmovie.data.model.remote.MovieRemote

data class MovieRepository(
    @PrimaryKey val id: String,
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
    var favourite: Boolean = false
)

fun MovieRepository.toLocal() = MovieLocal(
    id = id,
    title = title,
    original_title_romanised = originalTitleRomanised,
    image = image,
    movie_banner = movieBanner,
    description = description,
    director = director,
    producer = producer,
    release_date = releaseDate.toString(),
    running_time = runningTime,
    rt_score = rtScore.toString(),
    favourite = favourite
)

fun List<MovieRepository>.toLocal() : List<MovieLocal> {
    return this.map { it.toLocal() }
}

fun MovieLocal.toRepository() = MovieRepository(
    id = id,
    title = title,
    originalTitleRomanised = original_title_romanised,
    image = image,
    movieBanner = movie_banner,
    description = description,
    director = director,
    producer = producer,
    releaseDate = release_date.toInt(),
    runningTime = running_time,
    rtScore = rt_score.toInt(),
    favourite = favourite
)

fun List<MovieLocal>.toRepository() : List<MovieRepository> {
    return this.map { it.toRepository() }
}

fun MovieRemote.toRepository() = MovieRepository(
    id = id,
    title = title,
    originalTitleRomanised = original_title_romanised,
    image = image,
    movieBanner = movie_banner,
    description = description,
    director = director,
    producer = producer,
    releaseDate = release_date.toInt(),
    runningTime = running_time,
    rtScore = rt_score.toInt()
)

fun List<MovieRemote>.toRepository() : List<MovieRepository> {
    return this.map { it.toRepository() }
}