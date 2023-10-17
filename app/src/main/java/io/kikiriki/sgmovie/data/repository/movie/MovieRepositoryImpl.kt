package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.data.model.domain.toLocal
import io.kikiriki.sgmovie.data.model.local.toDomain
import io.kikiriki.sgmovie.data.model.remote.toDomain
import io.kikiriki.sgmovie.utils.Constants.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remote: MovieRepository.RemoteDataSource,
    private val local: MovieRepository.LocalDataSource,
    private val mock: MovieRepository.MockDataSource
) : MovieRepository {

    override suspend fun get(): Flow<List<Movie>> = flow {
        if (Repository.MOCK) {
           mock.get()
               .onEach { emit(it) }
               .catch { throw it }
               .collect()

        } else {

            // update or insert data from internet into our database
            val remoteData = remote.get().getOrThrow().toDomain().toLocal()
            local.insert(remoteData)

            // start to listen changes in database
            local.get()
                .onEach { emit(it.toDomain()) }
                .catch { throw it }
                .collect()
        }
    }

    override suspend fun update(movie: Movie): Result<Boolean> {
        if (Repository.MOCK) {
            return mock.update(movie)
        }

        return try {
            val localMovie = movie.toLocal()
            val result = local.update(localMovie).getOrThrow()
            Result.success(result)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

}