package io.kikiriki.sgmovie.data.framework.hilt

import androidx.annotation.Keep
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.kikiriki.sgmovie.data.repository.movie.remote.MovieEndpoints
import io.kikiriki.sgmovie.data.utils.Constants.Repository.URL
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
    fun provideProductEndpoints(okHttp: OkHttpClient) : MovieEndpoints {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .baseUrl(URL)
            .client(okHttp).build()
            .create(MovieEndpoints::class.java)
    }

}