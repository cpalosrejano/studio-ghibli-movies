package io.kikiriki.sgmovie.ui.main

import io.kikiriki.sgmovie.data.model.domain.Movie

open class MainUIState(
    val isLoading: Boolean = false,
    val error: Int? = null
) {

    // Response when activity call getMovies
    data class OnMoviesReady(
        val items: List<Movie> = listOf(),
    ) : MainUIState()

    // Response when activity call updateMovie
    data class OnMovieUpdated(
        val position: Int,
        val movie: Movie
    ) : MainUIState()
}