package io.kikiriki.sgmovie.data.preferences

import android.content.SharedPreferences
import io.kikiriki.sgmovie.domain.preferences.PreferenceStorage

class PreferenceStorageImpl(
    private val preferences: SharedPreferences
) : PreferenceStorage {

    companion object {
        private const val KEY_LANGUAGE = "lang"
        private const val KEY_LAST_REQUEST_TIMESTAMP = "lastRequestTimestamp"
    }

    override fun getLanguage(): String? {
        return preferences.getString(KEY_LANGUAGE, null)
    }

    override fun setLanguage(language: String) {
        preferences.edit().putString(KEY_LANGUAGE, language).apply()
    }

    override fun setTimestampLastRequest(timestamp: Long) {
        preferences.edit().putLong(KEY_LAST_REQUEST_TIMESTAMP, timestamp).apply()
    }

    override fun getTimestampLastRequest(): Long {
        return preferences.getLong(KEY_LAST_REQUEST_TIMESTAMP, 0)
    }
}