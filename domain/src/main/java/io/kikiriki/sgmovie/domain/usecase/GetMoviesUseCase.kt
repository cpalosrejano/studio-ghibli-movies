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

        // get data from remote config
        val apiCacheMillis = TimeUnit.HOURS.toMillis(remoteConfig.getApiCacheHour())
        val isMaintenanceEnabled = remoteConfig.isMaintenanceEnabled()

        // check if we need to fetch / refresh data from api
        val shouldRefreshData =
            !isMaintenanceEnabled &&
                (savedLanguage == null || savedLanguage != currentLanguage || // lang has changed or null
                (currentTimeMillis - lastRequestTimestamp) > apiCacheMillis) // or cache time has passed

        // update preferences if we need to refresh
        if (shouldRefreshData) {
            preferenceStorage.setLanguage(currentLanguage)
            preferenceStorage.setTimestampLastRequest(currentTimeMillis)
        }

        // which source should use to get movies
        val apiSource = when (remoteConfig.shouldUseRenderApi()) {
            true -> { MovieRepository.API.RENDER }
            else -> { MovieRepository.API.VERCEL }
        }

        return@withContext movieRepository.getMovies(
            forceRefresh = shouldRefreshData,
            lang = currentLanguage,
            api = apiSource
        )
    }

}