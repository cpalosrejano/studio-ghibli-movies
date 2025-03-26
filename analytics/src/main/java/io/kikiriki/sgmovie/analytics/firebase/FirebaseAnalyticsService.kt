package io.kikiriki.sgmovie.analytics.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import io.kikiriki.sgmovie.analytics.AnalyticEvent
import io.kikiriki.sgmovie.analytics.AnalyticsService
import javax.inject.Inject

class FirebaseAnalyticsService @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsService {

    override fun logEvent(event: AnalyticEvent, params: Map<String, Any?>) {
        val bundle = Bundle().apply {
            params.forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Double -> putDouble(key, value)
                    is Boolean -> putBoolean(key, value)
                    else -> putString(key, value.toString())
                }
            }
        }
        firebaseAnalytics.logEvent(event.name, bundle)
    }

    override fun setUserId(userId: String?) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperty(name: String, value: String?) {
        firebaseAnalytics.setUserProperty(name, value)
    }
}