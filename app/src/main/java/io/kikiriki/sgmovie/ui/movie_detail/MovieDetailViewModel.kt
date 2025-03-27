package io.kikiriki.sgmovie.ui.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.analytics.AnalyticEvent
import io.kikiriki.sgmovie.analytics.AnalyticsService
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.StreamingProvider
import io.kikiriki.sgmovie.domain.model.WatchProviders
import io.kikiriki.sgmovie.domain.usecase.GetMovieByIdUseCase
import io.kikiriki.sgmovie.domain.usecase.GetStreamingProviderUseCase
import io.kikiriki.sgmovie.domain.usecase.UpdateMovieLikeUseCase
import io.kikiriki.sgmovie.utils.ExceptionManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val getStreamingProviderUseCase: GetStreamingProviderUseCase,
    private val updateMovieLikeUseCase: UpdateMovieLikeUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val analyticsService: AnalyticsService,
) : ViewModel() {

    private val _error: MutableLiveData<Int?> = MutableLiveData(null)
    val error: LiveData<Int?> = _error

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _streamingProviders = MutableLiveData<WatchProviders?>(null)
    val streamingProviders = _streamingProviders

    private val _streamingProviderError: MutableLiveData<Int?> = MutableLiveData(null)
    val streamingProviderError: LiveData<Int?> = _streamingProviderError

    fun getMovieById(movieId: String) = viewModelScope.launch {
        getMovieByIdUseCase(movieId)
            .onEach { movie ->
                _movie.postValue(movie)
                // fetch streaming provider if are still null
                if (_streamingProviders.value == null) {
                    fetchStreamingProviders(movie)
                }
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

    fun onClickMovieLike() {
        val movie = movie.value ?: return
        val like = !movie.like // change to new state
        when (like) {
            true -> {
                analyticsService.logEvent(AnalyticEvent.MovieDetail.MOVIE_LIKE_TRUE)
            }
            false -> {
                analyticsService.logEvent(AnalyticEvent.MovieDetail.MOVIE_LIKE_FALSE)
            }
        }
    }

    fun onStreamingProviderSelected(streamingProvider: StreamingProvider) {
        val params = mapOf(
            "name" to streamingProvider.name.lowercase(),
            "type" to streamingProvider.type.name.lowercase()
        )
        analyticsService.logEvent(AnalyticEvent.MovieDetail.STREAMING_PROVIDER, params)
    }

    fun logScreenView() {
        val screenName = "MovieDetail"
        val screenClass = MovieDetailFragment::class.java.simpleName
        analyticsService.logScreenView(screenName, screenClass)
    }

    private fun fetchStreamingProviders(movie: Movie) = viewModelScope.launch {
        val countryCode = Locale.getDefault().country
        getStreamingProviderUseCase(movie, countryCode).fold(
            onSuccess = { data ->
                _streamingProviders.postValue(data)
            },
            onFailure = { failure ->
                val error = ExceptionManager.getMessage(failure)
                _streamingProviderError.postValue(error)
            }
        )

    }
}