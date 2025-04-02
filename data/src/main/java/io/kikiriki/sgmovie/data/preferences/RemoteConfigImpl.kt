package io.kikiriki.sgmovie.data.preferences

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.API_CACHE_HOURS
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.ENABLE_CONTACT
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.ENABLE_MAINTENANCE
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.ENABLE_PAYPAL
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.MIN_APP_VERSION
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig.Companion.MOVIE_LIKES_REFRESH_IN_SECONDS
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteConfigImpl @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : RemoteConfig {

    override suspend fun getFirestoreRefreshSeconds(): Long {
        remoteConfig.fetchAndActivate().await()
        return remoteConfig.getLong(MOVIE_LIKES_REFRESH_IN_SECONDS)
    }
    override suspend fun getApiCacheHour(): Long {
        remoteConfig.fetchAndActivate().await()
        return remoteConfig.getLong(API_CACHE_HOURS)
    }
    override suspend fun isPaypalEnabled(): Boolean {
        remoteConfig.fetchAndActivate().await()
        return remoteConfig.getBoolean(ENABLE_PAYPAL)
    }
    override suspend fun isMaintenanceEnabled(): Boolean {
        remoteConfig.fetchAndActivate().await()
        return remoteConfig.getBoolean(ENABLE_MAINTENANCE)
    }
    override suspend fun getMinAppVersion(): Long {
        remoteConfig.fetchAndActivate().await()
        return remoteConfig.getLong(MIN_APP_VERSION)
    }
    override suspend fun isContactEnabled(): Boolean {
        remoteConfig.fetchAndActivate().await()
        return remoteConfig.getBoolean(ENABLE_CONTACT)
    }
}