package io.kikiriki.sgmovie.data.preferences

import android.content.SharedPreferences
import io.kikiriki.sgmovie.domain.preferences.PreferenceStorage

class PreferenceStorageImpl(
    private val preferences: SharedPreferences
) : PreferenceStorage {

    companion object {
        private const val KEY_LANGUAGE = "lang"
    }

    override fun getLanguage(): String? {
        return preferences.getString(KEY_LANGUAGE, null)
    }

    override fun setLanguage(language: String) {
        preferences.edit().putString(KEY_LANGUAGE, language).apply()
    }
}