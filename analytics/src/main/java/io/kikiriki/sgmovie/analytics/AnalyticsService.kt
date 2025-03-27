package io.kikiriki.sgmovie.analytics

interface AnalyticsService {
    fun logScreenView(screenName: String, screenClass: String)
    fun logEvent(event: AnalyticEvent, params: Map<String, Any?> = emptyMap())
    fun setUserId(userId: String?)
    fun setUserProperty(name: String, value: String?)
}