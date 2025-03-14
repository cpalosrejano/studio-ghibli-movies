package io.kikiriki.sgmovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import io.kikiriki.sgmovie.domain.usecase.GetMoviesUseCase
import io.kikiriki.sgmovie.domain.usecase.UpdateMovieLikeUseCase
import io.kikiriki.sgmovie.utils.ExceptionManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateMovieLikeUseCase: UpdateMovieLikeUseCase,
) : ViewModel() {

    private val _uiState: MutableLiveData<MainUIState> = MutableLiveData(MainUIState())
    val uiState: LiveData<MainUIState> = _uiState

    fun getMovies() = viewModelScope.launch {
        _uiState.postValue(MainUIState(isLoading = true))

        getMoviesUseCase()
            .onEach {
                when (it) {

                    is GResult.Success -> {
                        val movies = it.data
                        _uiState.value = MainUIState(items = movies)
                    }
                    is GResult.Error -> {
                        val errorSt = ExceptionManager.getMessage(it.error)
                        _uiState.value = MainUIState(error = errorSt)
                    }
                    is GResult.SuccessWithError -> {
                        val movies = it.data
                        val errorSt = ExceptionManager.getMessage(it.error)
                        _uiState.value = MainUIState(items = movies, message = errorSt)
                    }

                }
            }
            .catch {
                val error = ExceptionManager.getMessage(it)
                _uiState.value = MainUIState(error = error)
            }
            .collect()
    }

    fun updateMovie(movie: Movie) = viewModelScope.launch {
        val newMovieStatus = movie.copy(like = !movie.like)
        when (val result = updateMovieLikeUseCase(newMovieStatus)) {
            is GResult.Success -> {}
            is GResult.SuccessWithError -> {
                // get the exception and send to the UI
                val error = ExceptionManager.getMessage(result.error)
                _uiState.value = MainUIState(error = error)
            }
            is GResult.Error -> {
                // get the exception and send to the UI
                val error = ExceptionManager.getMessage(result.error)
                _uiState.value = MainUIState(error = error)
            }
        }
    }

}