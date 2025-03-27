package io.kikiriki.sgmovie.ui.settings

import androidx.lifecycle.ViewModel
import io.kikiriki.sgmovie.analytics.AnalyticEvent
import io.kikiriki.sgmovie.analytics.AnalyticsService
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val analyticsService: AnalyticsService,
) : ViewModel() {

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