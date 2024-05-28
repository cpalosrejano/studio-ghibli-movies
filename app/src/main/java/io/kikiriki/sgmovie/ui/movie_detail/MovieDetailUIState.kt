package io.kikiriki.sgmovie.ui.movie_detail

import io.kikiriki.sgmovie.domain.model.Movie

data class MovieDetailUIState(
    val error: Int? = null,
    val movie: Movie? = null
)

