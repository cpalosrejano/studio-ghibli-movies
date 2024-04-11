package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.data.model.toLocal
import io.kikiriki.sgmovie.data.model.toRepository
import io.kikiriki.sgmovie.data.utils.Constants.Repository
import io.kikiriki.sgmovie.framework.hilt.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remote: MovieRepository.RemoteDataSource,
    private val local: MovieRepository.LocalDataSource,
    private val mock: MovieRepository.MockDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun get(): Flow<List<io.kikiriki.sgmovie.data.model.MovieRepository>> = flow {
        if (Repository.MOCK) {
           mock.get()
               .flowOn(dispatcher)
               .onEach { emit(it) }
               .catch { throw it }
               .collect()

        } else {

            // update or insert data from internet into our database
            val remoteData = remote.get().getOrThrow().toRepository().toLocal()

            local.insert(remoteData)

            // start to listen changes in database
            local.get()
                .flowOn(dispatcher)
                .onEach { emit(it.toRepository()) }
                .catch { throw it }
                .collect()
        }
    }

    override suspend fun update(movie: io.kikiriki.sgmovie.data.model.MovieRepository): Result<Boolean> = withContext(dispatcher) {
        if (Repository.MOCK) {
            return@withContext mock.update(movie)
        }

        return@withContext try {
            val localMovie = movie.toLocal()
            val result = local.update(localMovie).getOrThrow()
            Result.success(result)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

}