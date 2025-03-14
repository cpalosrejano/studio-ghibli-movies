package io.kikiriki.sgmovie.data.repository.movie.firestore

import kotlinx.coroutines.flow.Flow

interface MovieFirestoreDataSource {
    fun getLikes(): Flow<Map<String, Long>>
    fun updateLike(movieId: String, like: Boolean)
}