package io.kikiriki.sgmovie.ui.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.domain.movie.UpdateMovieUseCase
import io.kikiriki.sgmovie.utils.ExceptionManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val updateMovieUseCase: UpdateMovieUseCase
) : ViewModel() {

    private val _uiState: MutableLiveData<MovieDetailUIState> = MutableLiveData(MovieDetailUIState())
    val uiState: LiveData<MovieDetailUIState> = _uiState

    // update the status of favourite movie
    fun updateMovieFavourite(movie: Movie) = viewModelScope.launch {
        try {
            // get the result and send to the UI
            val result = updateMovieUseCase(movie).getOrThrow()
            _uiState.value = MovieDetailUIState.OnMovieUpdated(movie)

        } catch (e: Exception) {
            // get the exception and send to the UI
            val error = ExceptionManager.getMessage(e)
            _uiState.value = MovieDetailUIState(error = error)
        }
    }

}