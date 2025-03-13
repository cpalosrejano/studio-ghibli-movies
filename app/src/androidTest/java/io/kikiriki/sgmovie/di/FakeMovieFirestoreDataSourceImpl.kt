package io.kikiriki.sgmovie.di

import io.kikiriki.sgmovie.data.repository.movie.firestore.MovieFirestoreDataSource
import javax.inject.Inject

class FakeMovieFirestoreDataSourceImpl @Inject constructor() : MovieFirestoreDataSource