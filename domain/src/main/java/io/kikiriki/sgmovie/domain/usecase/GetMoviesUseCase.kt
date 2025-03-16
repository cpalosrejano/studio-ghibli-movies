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
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val preferenceStorage: PreferenceStorage,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() : Flow<GResult<List<Movie>, Throwable>> = withContext(dispatcher) {

        var forceRefresh = false
        val systemLang = Locale.getDefault().toLanguageTag()
        val storedLang = preferenceStorage.getLanguage()

        // save current system language and force refresh
        if (storedLang == null || storedLang != systemLang) {
            preferenceStorage.setLanguage(systemLang)
            forceRefresh = true
        }

        return@withContext movieRepository.get(
            forceRefresh = forceRefresh,
            lang = systemLang
        )
    }

}