package io.kikiriki.sgmovie.domain.preferences

interface PreferenceStorage {
    fun getLanguage(): String?
    fun setLanguage(language: String)
    fun setTimestampLastRequest(timestamp: Long)
    fun getTimestampLastRequest() : Long
}