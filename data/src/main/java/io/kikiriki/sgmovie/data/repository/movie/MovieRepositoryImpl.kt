package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.model.mapper.MovieMapper
import io.kikiriki.sgmovie.data.utils.Constants
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.repository.MovieRepository
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
    private val remote: MovieRemoteDataSource,
    private val local: MovieLocalDataSource,
    private val mock: MovieMockDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun get(): Flow<List<Movie>> = flow {
        if (Constants.Repository.MOCK) {
           mock.get()
               .flowOn(dispatcher)
               .onEach { emit(it) }
               .catch { throw it }
               .collect()

        } else {

            // update or insert data from internet into our database
            val remoteData = remote.get().getOrThrow()
            val localData = MovieMapper.dataToLocal(MovieMapper.remoteToData(remoteData))

            local.insert(localData)

            // start to listen changes in database
            local.get()
                .flowOn(dispatcher)
                .onEach { emit( MovieMapper.localToData(it) ) }
                .catch { throw it }
                .collect()
        }
    }

    override suspend fun update(movie: Movie): Result<Boolean> = withContext(dispatcher) {
        if (Constants.Repository.MOCK) {
            return@withContext mock.update(movie)
        }

        return@withContext try {
            val localMovie = MovieMapper.dataToLocal(movie)
            val result = local.update(localMovie).getOrThrow()
            Result.success(result)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

}