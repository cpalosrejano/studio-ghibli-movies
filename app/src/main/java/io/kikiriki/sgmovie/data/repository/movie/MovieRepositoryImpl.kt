package io.kikiriki.sgmovie.data.repository.movie

import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.data.model.domain.toLocal
import io.kikiriki.sgmovie.data.model.local.toDomain
import io.kikiriki.sgmovie.data.model.remote.toDomain
import io.kikiriki.sgmovie.utils.Repository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remote: MovieRepository.RemoteDataSource,
    private val local: MovieRepository.LocalDataSource,
    private val mock: MovieRepository.MockDataSource
) : MovieRepository {

    override suspend fun getAll(): Result<List<Movie>> {
        if (Repository.MOCK) {
            return mock.getAll()
        }

        return try {
            val result = remote.getAll().getOrThrow()
            val data = result.toDomain()
            Result.success(data)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

    override suspend fun getDetail(movieId: String): Result<Movie> {
        if (Repository.MOCK) {
            return mock.getDetail(movieId)
        }

        return try {
            val result = remote.getDetail(movieId).getOrThrow()
            val data = result.toDomain()
            Result.success(data)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

    override suspend fun getFavorites(): Result<List<Movie>> {
        if (Repository.MOCK) {
            return mock.getFavorites()
        }

        return try {
            val result = local.getFavorites().getOrThrow()
            val data = result.toDomain()
            Result.success(data)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

    override suspend fun addFavorite(movie: Movie): Result<Boolean> {
        if (Repository.MOCK) {
            return mock.addFavorite(movie)
        }

        return try {
            val localMovie = movie.toLocal()
            val result = local.addFavorite(localMovie).getOrThrow()
            Result.success(result)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

    override suspend fun deleteFavorite(movie: Movie): Result<Boolean> {
        if (Repository.MOCK) {
            return mock.deleteFavorite(movie)
        }

        return try {
            val localMovie = movie.toLocal()
            val result = local.deleteFavorite(localMovie).getOrThrow()
            Result.success(result)
        } catch (failure: Exception) {
            Result.failure(failure)
        }
    }

}