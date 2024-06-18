package io.kikiriki.sgmovie.data.model

data class MovieFirestore(
    val id: String,
    val title: String,
    val original_title_romanised: String? = null,
    val image: String,
    val movie_banner: String? = null,
    val description: String,
    val director: String,
    val producer: String? = null,
    val release_year: Int,
    val duration: Int? = null,
    val rt_score: Int,
)
