package io.kikiriki.sgmovie.ui.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.usecase.GetMovieByIdUseCase
import io.kikiriki.sgmovie.domain.usecase.UpdateMovieLikeUseCase
import io.kikiriki.sgmovie.utils.ExceptionManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val updateMovieLikeUseCase: UpdateMovieLikeUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    private val _error: MutableLiveData<Int?> = MutableLiveData(null)
    val error: LiveData<Int?> = _error

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    fun getMovieById(movieId: String) = viewModelScope.launch {
        getMovieByIdUseCase(movieId)
            .onEach { movie ->
                _movie.postValue(movie)
            }
            .catch {
                val error = ExceptionManager.getMessage(it)
                _error.postValue(error)
            }
            .collect()
    }



    fun updateMovieLike() = viewModelScope.launch {
        // check current movie is not null
        var newMovieStatus = movie.value
        if (newMovieStatus == null) {
            _error.postValue(R.string.movie_detail_error_cannot_update_like)
            return@launch
        }

        // switch movie like
        newMovieStatus = newMovieStatus.copy(like = !newMovieStatus.like)

        // update in local and firestore
        val result = updateMovieLikeUseCase(newMovieStatus)
        result.fold(
            onSuccess = { success ->
                if (!success) {
                    _error.postValue(R.string.movie_detail_error_cannot_update_like)
                }
            },
            onFailure = { failure ->
                val error = ExceptionManager.getMessage(failure)
                _error.postValue(error)
            }
        )
    }

}