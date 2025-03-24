package io.kikiriki.sgmovie.data.model.movie

data class MovieRemote(
    val id: String,
    val title: String,
    val title_romanised: String? = null,
    val image_cartel: String,
    val image_banner: String? = null,
    val description: String,
    val director: String? = null,
    val producer: String? = null,
    val soundtrack: String? = null,
    val release_date: Int? = null,
    val running_time: Int? = null,
    val rt_score: Int? = null,
    val coproduction: Boolean? = null,
    val tmdb_id: String? = null
)
