package io.kikiriki.sgmovie.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kikiriki.sgmovie.analytics.AnalyticEvent
import io.kikiriki.sgmovie.analytics.AnalyticsService
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val analyticsService: AnalyticsService,
    private val remoteConfig: RemoteConfig
) : ViewModel() {

    private val _isPaypalEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val isPaypalEnabled: LiveData<Boolean> = _isPaypalEnabled
    private val _isContactEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val isContactEnabled: LiveData<Boolean> = _isContactEnabled

    fun initialize() = viewModelScope.launch {
        remoteConfig.isContactEnabled().let {
            _isContactEnabled.value = it
        }
        remoteConfig.isPaypalEnabled().let {
            _isPaypalEnabled.value = it
        }
    }

    fun onClickTranslateApp() {
        analyticsService.logEvent(AnalyticEvent.Settings.TRANSLATE)
    }

    fun onClickPrivacyPolicy() {
        analyticsService.logEvent(AnalyticEvent.Settings.PRIVACY_POLICY)
    }

    fun onClickRoadmap() {
        analyticsService.logEvent(AnalyticEvent.Settings.ROADMAP)
    }

}