package io.kikiriki.sgmovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.domain.movie.GetMoviesUseCase
import io.kikiriki.sgmovie.domain.movie.UpdateMovieUseCase
import io.kikiriki.sgmovie.utils.ExceptionManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateMovieUseCase: UpdateMovieUseCase
) : ViewModel() {

    private val _uiState: MutableLiveData<MainUIState> = MutableLiveData(MainUIState())
    val uiState: LiveData<MainUIState> = _uiState

    fun getMovies() = viewModelScope.launch {
        _uiState.value = MainUIState(isLoading = true)

        try {
            // get the result and send to the UI
            val result = getMoviesUseCase().getOrThrow()
            _uiState.value = MainUIState.OnMoviesReady(items = result)

        } catch (e: Exception) {
            // get the exception and send to the UI
            val error = ExceptionManager.getMessage(e)
            _uiState.value = MainUIState(error = error)
        }


    }

    fun updateMovieFavourite(position: Int, movie: Movie) = viewModelScope.launch {
        try {
            // get the result and send to the UI
            val result = updateMovieUseCase(movie).getOrThrow()
            _uiState.value = MainUIState.OnMovieUpdated(position, movie)

        } catch (e: Exception) {
            // get the exception and send to the UI
            val error = ExceptionManager.getMessage(e)
            _uiState.value = MainUIState(error = error)
        }
    }

}