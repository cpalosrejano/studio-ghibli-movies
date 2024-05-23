package io.kikiriki.sgmovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.usecase.GetMoviesUseCase
import io.kikiriki.sgmovie.domain.usecase.UpdateMovieUseCase
import io.kikiriki.sgmovie.utils.ExceptionManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateMovieUseCase: UpdateMovieUseCase,
) : ViewModel() {

    private val _uiState: MutableLiveData<MainUIState> = MutableLiveData(MainUIState())
    val uiState: LiveData<MainUIState> = _uiState

    fun getMovies() = viewModelScope.launch {
        _uiState.postValue(MainUIState(isLoading = true))

        getMoviesUseCase()
            .onEach {
                _uiState.value = (MainUIState(items = it))
            }
            .catch {
                val error = ExceptionManager.getMessage(it)
                _uiState.value = MainUIState(error = error)
            }
            .collect()
    }

    fun updateMovie(movie: Movie) = viewModelScope.launch {
        try {
            val newMovieStatus = movie.copy(favourite = !movie.favourite)
            updateMovieUseCase(newMovieStatus).getOrThrow()
        } catch (e: Exception) {
            // get the exception and send to the UI
            val error = ExceptionManager.getMessage(e)
            _uiState.value = MainUIState(error = error)
        }
    }

}