package io.kikiriki.sgmovie.domain.usecase

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.preferences.PreferenceStorage
import io.kikiriki.sgmovie.domain.preferences.RemoteConfig
import io.kikiriki.sgmovie.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val preferenceStorage: PreferenceStorage,
    private val remoteConfig: RemoteConfig,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() : Flow<Result<List<Movie>>> = withContext(dispatcher) {

        // get saved data
        val savedLanguage = preferenceStorage.getLanguage()
        val lastRequestTimestamp = preferenceStorage.getTimestampLastRequest()

        // get current data
        val currentLanguage = Locale.getDefault().toLanguageTag()
        val currentTimeMillis = System.currentTimeMillis()
        
        val apiCacheHours = remoteConfig.getApiCacheHour()
        val apiCacheMillis = TimeUnit.HOURS.toMillis(apiCacheHours)

        // check if we need to refresh data from api
        val shouldRefreshData =
            savedLanguage == null ||
            savedLanguage != currentLanguage ||
            (currentTimeMillis - lastRequestTimestamp) > apiCacheMillis

        // update preferences if we need to refresh
        if (shouldRefreshData) {
            preferenceStorage.setLanguage(currentLanguage)
            preferenceStorage.setTimestampLastRequest(currentTimeMillis)
        }

        return@withContext movieRepository.getMovies(
            forceRefresh = shouldRefreshData,
            lang = currentLanguage
        )
    }

}