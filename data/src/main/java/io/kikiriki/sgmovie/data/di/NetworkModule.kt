package io.kikiriki.sgmovie.data.di

import androidx.annotation.Keep
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.kikiriki.sgmovie.data.repository.movie.remoteVercel.MovieEndpoints
import io.kikiriki.sgmovie.data.repository.tmdb.remote.TMDBEndpoints
import io.kikiriki.sgmovie.data.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@Keep
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideBaseOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .callTimeout(0, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun provideMovieEndpoints(okHttp: OkHttpClient) : MovieEndpoints {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .baseUrl(Constants.Repository.URL_API_MOVIE)
            .client(okHttp).build()
            .create(MovieEndpoints::class.java)
    }

    @Provides
    fun provideTMDBEndpoints(okHttp: OkHttpClient) : TMDBEndpoints {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .baseUrl(Constants.Repository.URL_API_TMDB)
            .client(okHttp).build()
            .create(TMDBEndpoints::class.java)
    }

    @Singleton
    @Provides
    fun provideFirestoreClient() : FirebaseFirestore = Firebase.firestore

}