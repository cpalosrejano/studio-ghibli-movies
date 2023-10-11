package io.kikiriki.sgmovie.ui.main

import io.kikiriki.sgmovie.data.model.domain.Note

data class MainUIState(
    val isLoading: Boolean = false,
    val items: List<Note> = listOf(),
    val error: Int? = null
)