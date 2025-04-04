package io.kikiriki.sgmovie.domain.preferences

interface RemoteConfig {
    companion object {
        const val MOVIE_LIKES_REFRESH_IN_SECONDS = "movie_like_refresh_in_seconds"
        const val API_CACHE_HOURS = "api_cache_hours"
        const val ENABLE_PAYPAL = "enable_paypal"
        const val ENABLE_MAINTENANCE = "enable_maintenance"
        const val ENABLE_CONTACT = "enable_contact"
        const val MIN_APP_VERSION = "min_app_version"
        const val USE_RENDER_API = "use_render_api"
    }

    suspend fun getFirestoreRefreshSeconds(): Long
    suspend fun getApiCacheHour(): Long
    suspend fun isPaypalEnabled(): Boolean
    suspend fun isMaintenanceEnabled(): Boolean
    suspend fun getMinAppVersion() : Long
    suspend fun isContactEnabled() : Boolean
    suspend fun shouldUseRenderApi() : Boolean
}