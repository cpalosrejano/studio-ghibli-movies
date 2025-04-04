package io.kikiriki.sgmovie.data.di

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.kikiriki.sgmovie.data.preferences.RemoteConfigImpl
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig

@Module
@InstallIn(SingletonComponent::class)
abstract class AbsRemoteConfigModule {

    @Binds
    abstract fun bindRemoteConfig(implementation: RemoteConfigImpl) : RemoteConfig
}

@Module
@InstallIn(SingletonComponent::class)
object RemoteConfigModule {

    @Provides
    fun provideFirebaseRemoteConfig() : FirebaseRemoteConfig {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        })
        remoteConfig.setDefaultsAsync(getDefaultValues())
        return remoteConfig
    }

    private fun getDefaultValues() : Map<String, Any> {
        return mapOf(
            RemoteConfig.MOVIE_LIKES_REFRESH_IN_SECONDS to 120,  // 2 minutes
            RemoteConfig.API_CACHE_HOURS to 48,             // 12 hours
            RemoteConfig.ENABLE_PAYPAL to true,             // enable paypal by default
            RemoteConfig.ENABLE_MAINTENANCE to false,       // disable maintenance by default
            RemoteConfig.ENABLE_CONTACT to false,           // disable contact by default
            RemoteConfig.MIN_APP_VERSION to Long.MAX_VALUE, // disable min app version by default
            RemoteConfig.USE_RENDER_API to false,           // use vercel api by disabling render
        )
    }
}
