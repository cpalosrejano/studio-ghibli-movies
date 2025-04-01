package io.kikiriki.sgmovie.data.repository.movie.firestore

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MovieFirestoreDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
)  : MovieFirestoreDataSource {

    private companion object {
        const val COLLECTION_MOVIES_LIKE = "movies_likes"
        const val FIELD_LIKE_COUNT = "like_count"
    }

    override suspend fun getLikes(): Map<String, Long> {
        val document = firestore.collection(COLLECTION_MOVIES_LIKE).get().await()
        val likesMap = document.documents.associate { doc ->
            doc.id to (doc.getLong(FIELD_LIKE_COUNT) ?: 0)
        }
        return likesMap
    }

    override fun getMovieLikesById(movieId: String): Flow<Long> = callbackFlow {
        val docRef = firestore.collection(COLLECTION_MOVIES_LIKE).document(movieId)
        val listener = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val likeCount = snapshot?.getLong(FIELD_LIKE_COUNT) ?: 0
            trySend(likeCount)
        }
        awaitClose { listener.remove() }
    }

    override fun updateLike(movieId: String, like: Boolean) {
        val docRef = firestore.collection(COLLECTION_MOVIES_LIKE).document(movieId)
        val value = FieldValue.increment(if (like) 1 else -1)
        docRef.set(mapOf(FIELD_LIKE_COUNT to value), SetOptions.merge())
    }
}