package io.kikiriki.sgmovie.di

import io.kikiriki.sgmovie.data.repository.movie.firestore.MovieFirestoreDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeMovieFirestoreDataSourceImpl @Inject constructor() : MovieFirestoreDataSource {

    private val moviesLike = hashMapOf<String, Long>(
        "some-id" to 3,
        "another-id" to 1
    )

    override fun getLikes(): Flow<Map<String, Long>> = flowOf(moviesLike)

    override fun getMovieLikesById(movieId: String): Flow<Long> {
        return flowOf(1)
    }

    override fun updateLike(movieId: String, like: Boolean) {
        if(moviesLike.containsKey(movieId)) {
            val actualLikes = moviesLike[movieId] ?: 0
            moviesLike[movieId] = actualLikes + (if (like) 1 else -1)
        } else {
            moviesLike[movieId] = 1
        }
    }
}