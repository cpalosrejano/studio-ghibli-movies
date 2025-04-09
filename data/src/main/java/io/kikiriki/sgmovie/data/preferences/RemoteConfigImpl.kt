package io.kikiriki.sgmovie.data.preferences

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.API_CACHE_HOURS
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.ENABLE_CONTACT
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.ENABLE_MAINTENANCE
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.ENABLE_PAYPAL
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.MIN_APP_VERSION
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.MOVIE_LIKES_REFRESH_IN_SECONDS
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.USE_RENDER_API
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteConfigImpl @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : RemoteConfig {

    override suspend fun getFirestoreRefreshSeconds(): Long {
        fetchAndActivateConfigs()
        return remoteConfig.getLong(MOVIE_LIKES_REFRESH_IN_SECONDS)
    }
    override suspend fun getApiCacheHour(): Long {
        fetchAndActivateConfigs()
        return remoteConfig.getLong(API_CACHE_HOURS)
    }
    override suspend fun isPaypalEnabled(): Boolean {
        fetchAndActivateConfigs()
        return remoteConfig.getBoolean(ENABLE_PAYPAL)
    }
    override suspend fun isMaintenanceEnabled(): Boolean {
        fetchAndActivateConfigs()
        return remoteConfig.getBoolean(ENABLE_MAINTENANCE)
    }
    override suspend fun getMinAppVersion(): Long {
        fetchAndActivateConfigs()
        return remoteConfig.getLong(MIN_APP_VERSION)
    }
    override suspend fun isContactEnabled(): Boolean {
        fetchAndActivateConfigs()
        return remoteConfig.getBoolean(ENABLE_CONTACT)
    }
    override suspend fun shouldUseRenderApi(): Boolean {
        fetchAndActivateConfigs()
        return remoteConfig.getBoolean(USE_RENDER_API)
    }

    private suspend fun fetchAndActivateConfigs() : Boolean {
        return try {
            remoteConfig.fetchAndActivate().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}