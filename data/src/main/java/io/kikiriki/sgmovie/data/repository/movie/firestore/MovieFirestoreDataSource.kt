package io.kikiriki.sgmovie.data.repository.movie.firestore


interface MovieFirestoreDataSource {
    suspend fun getLikes(): Map<String, Long>
    fun updateLike(movieId: String, like: Boolean)
}