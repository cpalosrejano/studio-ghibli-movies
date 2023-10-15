package io.kikiriki.sgmovie.ui.movie_detail

import io.kikiriki.sgmovie.data.model.domain.Movie

open class MovieDetailUIState(
    val error: Int? = null,
) {

    // Response when fragment call update favourite
    data class OnMovieUpdated(
        val movie: Movie
    ) : MovieDetailUIState()
}

