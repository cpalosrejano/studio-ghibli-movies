package io.kikiriki.sgmovie.ui.main

import io.kikiriki.sgmovie.domain.model.Movie

data class MainUIState(
    val isLoading: Boolean = false,
    val error: Int? = null,
    val message: Int? = null,
    val items: List<Movie> = listOf()
)