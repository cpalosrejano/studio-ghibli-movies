package io.kikiriki.sgmovie.ui.movie_detail

import io.kikiriki.sgmovie.data.model.domain.Movie

data class MovieDetailUIState(
    val error: Int? = null,
    val movie: Movie? = null
)

