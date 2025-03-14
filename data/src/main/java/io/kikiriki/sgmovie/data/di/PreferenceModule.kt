package io.kikiriki.sgmovie.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.kikiriki.sgmovie.data.AppDatabase
import io.kikiriki.sgmovie.data.preferences.PreferenceStorageImpl
import io.kikiriki.sgmovie.data.repository.movie.firestore.MovieFirestoreDataSource
import io.kikiriki.sgmovie.data.repository.movie.firestore.MovieFirestoreDataSourceImpl
import io.kikiriki.sgmovie.data.repository.movie.local.MovieDao
import io.kikiriki.sgmovie.data.utils.DBMigrations
import io.kikiriki.sgmovie.domain.preferences.PreferenceStorage

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    private const val PREFERENCES_NAME = "sgm_prefs"

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) : SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideStoragePreference(sp: SharedPreferences) : PreferenceStorage {
        return PreferenceStorageImpl(sp)
    }
}
