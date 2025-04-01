package io.kikiriki.sgmovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.analytics.AnalyticEvent
import io.kikiriki.sgmovie.analytics.AnalyticsService
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig
import io.kikiriki.sgmovie.domain.usecase.GetMoviesUseCase
import io.kikiriki.sgmovie.domain.usecase.UpdateMovieLikeUseCase
import io.kikiriki.sgmovie.model.Sort
import io.kikiriki.sgmovie.utils.ExceptionManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateMovieLikeUseCase: UpdateMovieLikeUseCase,
    private val analyticsService: AnalyticsService,
    private val remoteConfig: RemoteConfig
) : ViewModel() {

    private val _uiState: MutableLiveData<MainUIState> = MutableLiveData(MainUIState())
    val uiState: LiveData<MainUIState> = _uiState

    private val _appConfig = MutableLiveData<AppConfig>()
    val appConfig: LiveData<AppConfig> = _appConfig

    fun getAppConfig() = viewModelScope.launch {
        _uiState.postValue(MainUIState(isLoading = true))
        _appConfig.postValue(AppConfig(
            minAppVersion = remoteConfig.getMinAppVersion(),
            isMaintenance = remoteConfig.isMaintenanceEnabled()
        ))
    }

    fun getMovies() = viewModelScope.launch {
        _uiState.postValue(MainUIState(isLoading = true))

        getMoviesUseCase()
            .onEach { result ->

                result.fold(
                    onSuccess = { movies ->
                        _uiState.value = MainUIState(items = movies)
                    },
                    onFailure = { error ->
                        val errorSt = ExceptionManager.getMessage(error)
                        _uiState.value = MainUIState(error = errorSt)
                    }
                )
            }
            .catch {
                val error = ExceptionManager.getMessage(it)
                _uiState.value = MainUIState(error = error)
            }
            .collect()
    }

    fun updateMovie(movie: Movie) = viewModelScope.launch {
        updateMovieLikeUseCase(movie).fold(
            onSuccess = { success ->
                if (!success) {
                    _uiState.value = MainUIState(error = R.string.movie_detail_error_cannot_update_like)
                }
            },
            onFailure = { failure ->
                val error = ExceptionManager.getMessage(failure)
                _uiState.value = MainUIState(error = error)
            }
        )
    }

    fun onClickMovieLike(movie: Movie) {
        val newLikeStatus = !movie.like // change to new like state
        if (newLikeStatus) {
            analyticsService.logEvent(AnalyticEvent.MainScreen.MOVIE_LIKE_TRUE)
        } else {
            analyticsService.logEvent(AnalyticEvent.MainScreen.MOVIE_LIKE_FALSE)
        }
    }

    fun onSortDialogOptionSelected(sortType: Sort) {
        val eventName = when (sortType.type) {
            Sort.Type.LIKE -> AnalyticEvent.SortDialog.SORT_BY_LIKES_MINE
            Sort.Type.LIKE_COUNT -> AnalyticEvent.SortDialog.SORT_BY_LIKES_COUNT
            Sort.Type.NAME -> AnalyticEvent.SortDialog.SORT_BY_NAME
            Sort.Type.SCORE -> AnalyticEvent.SortDialog.SORT_BY_SCORE
            Sort.Type.DIRECTOR -> AnalyticEvent.SortDialog.SORT_BY_DIRECTOR
            Sort.Type.YEAR -> AnalyticEvent.SortDialog.SORT_BY_YEAR
        }
        analyticsService.logEvent(eventName)
    }

    fun onSortDialogCancel() {
        analyticsService.logEvent(AnalyticEvent.SortDialog.CANCEL)
    }

    fun onSortDialogOpened() {
        analyticsService.logEvent(AnalyticEvent.SortDialog.OPEN)
    }

}