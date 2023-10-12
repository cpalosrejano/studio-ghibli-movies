package io.kikiriki.sgmovie.ui.main

import io.kikiriki.sgmovie.data.model.domain.Movie

data class MainUIState(
    val isLoading: Boolean = false,
    val items: List<Movie> = listOf(),
    val error: Int? = null
)