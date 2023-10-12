package io.kikiriki.sgmovie.data.model.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.kikiriki.sgmovie.data.model.domain.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MovieLocal(
    @PrimaryKey val id: String,
    val title: String,
    val original_title_romanised: String,
    val image: String,
    val movie_banner: String? = null,
    val description: String,
    val director: String,
    val producer: String? = null,
    val release_date: String? = null,
    val running_time: String? = null,
    val rt_score: String
): Parcelable

fun MovieLocal.toDomain() = Movie(
    id = id,
    title = title,
    originalTitleRomanised = original_title_romanised,
    image = image,
    movieBanner = movie_banner,
    description = description,
    director = director,
    producer = producer,
    releaseDate = release_date,
    runningTime = running_time,
    rtScore = rt_score
)

fun List<MovieLocal>.toDomain() : List<Movie> {
    return this.map { it.toDomain() }
}