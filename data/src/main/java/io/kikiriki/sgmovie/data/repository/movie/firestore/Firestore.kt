package io.kikiriki.sgmovie.data.repository.movie.firestore

object Firestore {

    object Movies {
        const val COLLECTION_NAME = "movies"
        const val KEY_ID = "id"
        const val KEY_TITLE = "title"
        const val KEY_ORIGINAL_TITLE_ROMANISED = "original_title_romanised"
        const val KEY_IMAGE = "image"
        const val KEY_MOVIE_BANNER = "movie_banner"
        const val KEY_DESCRIPTION = "description"
        const val KEY_DIRECTOR = "director"
        const val KEY_PRODUCER = "producer"
        const val KEY_RELEASE_YEAR = "release_year"
        const val KEY_DURATION = "duration"
        const val KEY_RT_SCORE = "rt_score"
    }
}