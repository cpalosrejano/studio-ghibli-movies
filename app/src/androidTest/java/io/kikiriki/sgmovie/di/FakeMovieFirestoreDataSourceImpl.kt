package io.kikiriki.sgmovie.di

import io.kikiriki.sgmovie.data.repository.movie.firestore.MovieFirestoreDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeMovieFirestoreDataSourceImpl @Inject constructor() : MovieFirestoreDataSource {

    private val storedLikes = mutableMapOf(
        "758bf02e-3122-46e0-884e-67cf83df1786" to 4L,
        "dc2e6bd1-8156-4886-adff-b39e6043af0c" to 2L,
        "58611129-2dbc-4a81-a72f-77ddfc1b1b49" to 3L
    )

    override suspend fun getLikes(): Map<String, Long> = storedLikes

    override fun getMovieLikesById(movieId: String): Flow<Long> {
        return flowOf(1)
    }

    override fun updateLike(movieId: String, like: Boolean) {
        if(storedLikes.containsKey(movieId)) {
            val actualLikes = storedLikes[movieId] ?: 0
            storedLikes[movieId] = actualLikes + (if (like) 1 else -1)
        } else {
            storedLikes[movieId] = 1
        }
    }
}