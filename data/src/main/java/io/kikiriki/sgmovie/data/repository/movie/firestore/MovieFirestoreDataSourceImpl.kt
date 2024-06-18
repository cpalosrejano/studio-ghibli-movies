package io.kikiriki.sgmovie.data.repository.movie.firestore

import com.google.firebase.firestore.FirebaseFirestore
import io.kikiriki.sgmovie.core.coroutines.di.IODispatcher
import io.kikiriki.sgmovie.data.exception.RemoteDataSourceException
import io.kikiriki.sgmovie.data.model.MovieFirestore
import io.kikiriki.sgmovie.data.model.mapper.MovieMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MovieFirestoreDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    @IODispatcher private val dispatcher: CoroutineDispatcher
)  : MovieFirestoreDataSource {

    override suspend fun get(): List<MovieFirestore> = withContext(dispatcher) {
        return@withContext try {
            val result: List<MovieFirestore> = getFirestoreMovies()
            result
        } catch (failure: Exception) {
            val exception = handleException(failure)
            throw exception
        }
    }

    private suspend fun getFirestoreMovies() : List<MovieFirestore> = suspendCoroutine { continuation ->
        firestore.collection(Firestore.Movies.COLLECTION_NAME).get().addOnSuccessListener { moviesResult ->
            try {
                // get movies list
                val movies : List<MovieFirestore> = moviesResult.map { movieSnapshot ->
                    MovieMapper.snapshotToFirestore(movieSnapshot.data)
                }
                // return movies
                continuation.resume(movies)

            } catch (exception: Exception) {
                continuation.resumeWithException(exception)
            }

        }.addOnFailureListener { exception ->
            continuation.resumeWithException(exception)

        }
    }

    private fun handleException(exception: Exception) : RemoteDataSourceException {
        return when (exception) {

            is HttpException -> {
                RemoteDataSourceException(
                    code = when (exception.code()) {
                        401 -> { RemoteDataSourceException.Code.UNAUTHORIZED }
                        404 -> { RemoteDataSourceException.Code.RESOURCE_NOT_FOUND }
                        else -> { RemoteDataSourceException.Code.HTTP_UNKNOWN }
                    },
                    message = exception.message.orEmpty()
                )
            }

            is UnknownHostException -> {
                RemoteDataSourceException(
                    code = RemoteDataSourceException.Code.NO_INTERNET_CONNECTION,
                    message = exception.message.orEmpty()
                )
            }

            else -> {
                RemoteDataSourceException(
                    code = RemoteDataSourceException.Code.DEFAULT,
                    message = exception.message.orEmpty()
                )
            }

        }
    }

}