package io.kikiriki.sgmovie.data.repository.movie.firestore

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class MovieFirestoreDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
)  : MovieFirestoreDataSource