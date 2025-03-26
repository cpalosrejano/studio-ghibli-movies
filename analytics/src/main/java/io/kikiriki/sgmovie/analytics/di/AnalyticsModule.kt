package io.kikiriki.sgmovie.analytics.di

import androidx.annotation.Keep
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.kikiriki.sgmovie.analytics.AnalyticsService
import io.kikiriki.sgmovie.analytics.firebase.FirebaseAnalyticsService
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

    @Binds
    abstract fun bindFirebaseAnalyticsService(implementation: FirebaseAnalyticsService) : AnalyticsService

    @Module
    @Keep
    @InstallIn(SingletonComponent::class)
    object AnalyticsFirebaseModule {

        @Singleton
        @Provides
        fun provideFirebaseAnalytics() : FirebaseAnalytics =  Firebase.analytics

    }
}