package io.kikiriki.sgmovie.analytics

sealed class AnalyticEvent(val name: String) {

    sealed class MainScreen(eventName: String) : AnalyticEvent(eventName) {
        data object MOVIE_LIKE_TRUE : MainScreen("movie_list_like_true")
        data object MOVIE_LIKE_FALSE : MainScreen("movie_list_like_false")
    }

    sealed class SortDialog(eventName: String) : AnalyticEvent(eventName) {
        data object OPEN : SortDialog("sort_movies_open")
        data object SORT_BY_NAME : SortDialog("sort_movies_by_name")
        data object SORT_BY_YEAR : SortDialog("sort_movies_by_year")
        data object SORT_BY_DIRECTOR : SortDialog("sort_movies_by_director")
        data object SORT_BY_SCORE : SortDialog("sort_movies_by_score")
        data object SORT_BY_LIKES_COUNT : SortDialog("sort_movies_by_likes_count")
        data object SORT_BY_LIKES_MINE : SortDialog("sort_movies_by_likes_mine")
        data object CLOSE : SortDialog("sort_movies_close")
    }

    sealed class MovieDetail(eventName: String) : AnalyticEvent(eventName) {
        data object MOVIE_LIKE_TRUE : MovieDetail("movie_detail_like_true")
        data object MOVIE_LIKE_FALSE : MovieDetail("movie_detail_like_false")
        data object STREAMING_PROVIDER : MovieDetail("movie_detail_streaming_provider")
    }

    sealed class Settings(eventName: String) : AnalyticEvent(eventName) {
        data object TRANSLATE : Settings("settings_translate")
        data object PRIVACY_POLICY : Settings("settings_privacy_policy")
        data object ROADMAP : Settings("settings_roadmap")
    }
}