package io.kikiriki.sgmovie.domain.preferences

interface RemoteConfig {
    companion object {
        const val MOVIE_LIKES_REFRESH_IN_SECONDS = "movie_like_refresh_in_seconds"
        const val API_CACHE_HOURS = "api_cache_hours"
        const val ENABLE_PAYPAL = "enable_paypal"
        const val ENABLE_MAINTENANCE = "enable_maintenance"
    }

    suspend fun getFirestoreRefreshSeconds(): Long
    suspend fun getApiCacheHour(): Long
    suspend fun isPaypalEnabled(): Boolean
    suspend fun isMaintenanceEnabled(): Boolean
}