package io.kikiriki.sgmovie.framework.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.testing.TestInstallIn
import io.kikiriki.sgmovie.data.repository.movie.FakeMovieRepositoryImpl
import io.kikiriki.sgmovie.data.repository.movie.MovieRepository


@TestInstallIn(
    components = [ActivityComponent::class],
    replaces = [MovieRepositoryModule::class]
)
@Module
abstract class FakeMovieRepositoryModule {

    @Binds
    abstract fun bindMovieRepository(implementation: FakeMovieRepositoryImpl) : MovieRepository

}