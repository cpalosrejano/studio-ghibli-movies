package io.kikiriki.sgmovie.domain.usecase

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.base.GResult
import io.kikiriki.sgmovie.domain.preferences.PreferenceStorage
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
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    private val cacheTime = TimeUnit.HOURS.toMillis(12)

    suspend operator fun invoke() : Flow<GResult<List<Movie>, Throwable>> = withContext(dispatcher) {

        // get saved data
        val savedLanguage = preferenceStorage.getLanguage()
        val lastRequestTimestamp = preferenceStorage.getTimestampLastRequest()

        // get current data
        val currentLanguage = Locale.getDefault().toLanguageTag()
        val currentTimeMillis = System.currentTimeMillis()

        // check if we need to refresh data from api
        val shouldRefreshData =
            savedLanguage == null ||
            savedLanguage != currentLanguage ||
            (currentTimeMillis - lastRequestTimestamp) > cacheTime

        // update preferences if we need to refresh
        if (shouldRefreshData) {
            preferenceStorage.setLanguage(currentLanguage)
            preferenceStorage.setTimestampLastRequest(currentTimeMillis)
        }

        return@withContext movieRepository.get(
            forceRefresh = shouldRefreshData,
            lang = currentLanguage
        )
    }

}