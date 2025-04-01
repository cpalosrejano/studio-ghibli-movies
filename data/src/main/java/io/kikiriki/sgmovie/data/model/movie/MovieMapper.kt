package io.kikiriki.sgmovie.data.model.movie

import io.kikiriki.sgmovie.domain.model.Movie

object MovieMapper {

    fun remoteToData(movieRemote: MovieRemote) : Movie {
        return Movie(
            id = movieRemote.id,
            title = movieRemote.title,
            titleRomanised = movieRemote.title_romanised.orEmpty(),
            imageCartel = movieRemote.image_cartel,
            imageBanner = movieRemote.image_banner.orEmpty(),
            description = movieRemote.description,
            director = movieRemote.director.orEmpty(),
            producer = movieRemote.producer.orEmpty(),
            soundtrack = movieRemote.soundtrack.orEmpty(),
            releaseDate = movieRemote.release_date ?: 0,
            runningTime = movieRemote.running_time ?: 0,
            rtScore = movieRemote.rt_score ?: 0,
            coproduction = movieRemote.coproduction ?: false,
            tmdbId = movieRemote.tmdb_id.toString()
        )
    }
    fun remoteToData(moviesRemote: List<MovieRemote>) : List<Movie> {
        return moviesRemote.map { remoteToData(it) }
    }

    fun localToData(movieLocal: MovieLocal) : Movie {
        return Movie(
            id = movieLocal.id,
            title = movieLocal.title,
            titleRomanised = movieLocal.titleRomanised,
            imageCartel = movieLocal.imageCartel,
            imageBanner = movieLocal.imageBanner,
            description = movieLocal.description,
            director = movieLocal.director,
            producer = movieLocal.producer,
            soundtrack = movieLocal.soundtrack,
            releaseDate = movieLocal.releaseDate,
            runningTime = movieLocal.runningTime,
            rtScore = movieLocal.rtScore,
            coproduction = movieLocal.coproduction,
            likeCount = movieLocal.likeCount,
            like = movieLocal.like,
            tmdbId = movieLocal.tmdbId
        )
    }
    fun localToData(moviesLocal: List<MovieLocal>) : List<Movie> {
        return moviesLocal.map { localToData(it) }
    }

    fun dataToLocal(movie: Movie) : MovieLocal {
        return MovieLocal(
            id = movie.id,
            title = movie.title,
            titleRomanised = movie.titleRomanised,
            imageCartel = movie.imageCartel,
            imageBanner = movie.imageBanner,
            description = movie.description,
            director = movie.director,
            producer = movie.producer,
            soundtrack = movie.soundtrack,
            releaseDate = movie.releaseDate,
            runningTime = movie.runningTime,
            rtScore = movie.rtScore,
            coproduction = movie.coproduction,
            like = movie.like,
            likeCount  = movie.likeCount,
            tmdbId = movie.tmdbId
        )
    }
    fun dataToLocal(movies: List<Movie>) : List<MovieLocal> {
        return movies.map { dataToLocal(it) }
    }

}