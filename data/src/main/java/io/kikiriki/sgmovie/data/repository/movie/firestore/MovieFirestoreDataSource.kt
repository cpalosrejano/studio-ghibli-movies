package io.kikiriki.sgmovie.data.repository.movie.firestore

import io.kikiriki.sgmovie.data.model.MovieFirestore

interface MovieFirestoreDataSource {
    suspend fun get() : List<MovieFirestore>
}