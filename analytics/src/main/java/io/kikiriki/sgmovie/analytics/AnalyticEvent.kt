package io.kikiriki.sgmovie.analytics

sealed interface AnalyticEvent {
    val eventName: String

    enum class MainScreen(override val eventName: String) : AnalyticEvent {
        MOVIE_LIKE_TRUE("movie_list_like_true"),
        MOVIE_LIKE_FALSE("movie_list_like_false");
    }

    enum class SortDialog(override val eventName: String) : AnalyticEvent {
        OPEN("sort_movies_open"),
        SORT_BY_NAME("sort_movies_by_name"),
        SORT_BY_YEAR("sort_movies_by_year"),
        SORT_BY_DIRECTOR("sort_movies_by_director"),
        SORT_BY_SCORE("sort_movies_by_score"),
        SORT_BY_LIKES_COUNT("sort_movies_by_likes_count"),
        SORT_BY_LIKES_MINE("sort_movies_by_likes_mine"),
        CANCEL("sort_movies_cancel");
    }

    enum class MovieDetail(override val eventName: String) : AnalyticEvent {
        MOVIE_LIKE_TRUE("movie_detail_like_true"),
        MOVIE_LIKE_FALSE("movie_detail_like_false"),
        STREAMING_PROVIDER("movie_detail_streaming_provider");
    }

    enum class Settings(override val eventName: String) : AnalyticEvent {
        TRANSLATE("settings_translate"),
        PRIVACY_POLICY("settings_privacy_policy"),
        ROADMAP("settings_roadmap");
    }

    enum class ScreenView(override val eventName: String) : AnalyticEvent {
        MAIN_SCREEN("main_screen"),
        SORT_DIALOG("sort_dialog"),
        MOVIE_DETAIL("movie_detail"),
        SETTINGS("settings");
    }
}