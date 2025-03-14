package io.kikiriki.sgmovie.data.repository.movie.firestore

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MovieFirestoreDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
)  : MovieFirestoreDataSource {

    private companion object {
        const val COLLECTION_MOVIES_LIKE = "movies_likes"
        const val FIELD_LIKE_COUNT = "like_count"
    }

    override fun getLikes(): Flow<Map<String, Long>> = callbackFlow {
        val listener = firestore.collection(COLLECTION_MOVIES_LIKE)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val likesMap = snapshot.documents.associate { doc ->
                        doc.id to (doc.getLong(FIELD_LIKE_COUNT) ?: 0)
                    }
                    trySend(likesMap).isSuccess
                }
            }
        awaitClose { listener.remove() }
    }

    override fun updateLike(movieId: String, like: Boolean) {
        val docRef = firestore.collection(COLLECTION_MOVIES_LIKE).document(movieId)
        val value = FieldValue.increment(if (like) 1 else -1)
        docRef.set(mapOf(FIELD_LIKE_COUNT to value), SetOptions.merge())
    }
}