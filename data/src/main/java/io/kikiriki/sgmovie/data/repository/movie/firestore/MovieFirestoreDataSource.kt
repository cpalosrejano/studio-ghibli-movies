package io.kikiriki.sgmovie.data.repository.movie.firestore

import kotlinx.coroutines.flow.Flow

interface MovieFirestoreDataSource {
    suspend fun getLikes(): Map<String, Long>
    fun getMovieLikesById(movieId: String) : Flow<Long>
    fun updateLike(movieId: String, like: Boolean)
}