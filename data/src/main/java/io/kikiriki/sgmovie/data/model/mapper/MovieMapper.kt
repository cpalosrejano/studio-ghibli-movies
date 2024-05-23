package io.kikiriki.sgmovie.data.model.mapper

import io.kikiriki.sgmovie.data.model.MovieLocal
import io.kikiriki.sgmovie.data.model.MovieRemote
import io.kikiriki.sgmovie.domain.model.Movie

object MovieMapper {

    fun remoteToData(movieRemote: MovieRemote) : Movie {
        return Movie(
            id = movieRemote.id,
            title = movieRemote.title,
            originalTitleRomanised = movieRemote.original_title_romanised,
            image = movieRemote.image,
            movieBanner = movieRemote.movie_banner,
            description = movieRemote.description,
            director = movieRemote.director,
            producer = movieRemote.producer,
            releaseDate = movieRemote.release_date.toInt(),
            runningTime = movieRemote.running_time,
            rtScore = movieRemote.rt_score.toInt()
        )
    }

    fun remoteToData(moviesRemote: List<MovieRemote>) : List<Movie> {
        return moviesRemote.map { remoteToData(it) }
    }

    fun localToData(movieLocal: MovieLocal) : Movie {
        return Movie(
            id = movieLocal.id,
            title = movieLocal.title,
            originalTitleRomanised = movieLocal.original_title_romanised,
            image = movieLocal.image,
            movieBanner = movieLocal.movie_banner,
            description = movieLocal.description,
            director = movieLocal.director,
            producer = movieLocal.producer,
            releaseDate = movieLocal.release_date.toInt(),
            runningTime = movieLocal.running_time,
            rtScore = movieLocal.rt_score.toInt(),
            favourite = movieLocal.favourite
        )
    }
    fun localToData(moviesLocal: List<MovieLocal>) : List<Movie> {
        return moviesLocal.map { localToData(it) }
    }


    fun dataToRemote(movieRepository: Movie) : MovieRemote {
        return MovieRemote(
            id = movieRepository.id,
            title = movieRepository.title,
            original_title_romanised = movieRepository.originalTitleRomanised,
            image = movieRepository.image,
            movie_banner = movieRepository.movieBanner,
            description = movieRepository.description,
            director = movieRepository.director,
            producer = movieRepository.producer,
            release_date = movieRepository.releaseDate.toString(),
            running_time = movieRepository.runningTime,
            rt_score = movieRepository.rtScore.toString()
        )
    }
    fun dataToRemote(moviesRepository: List<Movie>) : List<MovieRemote> {
        return moviesRepository.map { dataToRemote(it) }
    }

    fun dataToLocal(movieRepository: Movie) : MovieLocal {
        return MovieLocal(
            id = movieRepository.id,
            title = movieRepository.title,
            original_title_romanised = movieRepository.originalTitleRomanised,
            image = movieRepository.image,
            movie_banner = movieRepository.movieBanner,
            description = movieRepository.description,
            director = movieRepository.director,
            producer = movieRepository.producer,
            release_date = movieRepository.releaseDate.toString(),
            running_time = movieRepository.runningTime,
            rt_score = movieRepository.rtScore.toString(),
            favourite = movieRepository.favourite
        )
    }
    fun dataToLocal(moviesRepository: List<Movie>) : List<MovieLocal> {
        return moviesRepository.map { dataToLocal(it) }
    }

}