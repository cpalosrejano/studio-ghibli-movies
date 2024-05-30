package io.kikiriki.sgmovie.data.model

data class MovieRemote(
    val id: String,
    val title: String,
    val original_title_romanised: String? = null,
    val image: String,
    val movie_banner: String? = null,
    val description: String,
    val director: String,
    val producer: String? = null,
    val release_date: String,
    val running_time: String? = null,
    val rt_score: String,
)
