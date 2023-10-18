package io.kikiriki.sgmovie.data.repository.movie.mock

import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.data.repository.movie.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MovieMockDataSource @Inject constructor()  : MovieRepository.MockDataSource {
    private val movies = mutableListOf(
        Movie(
            id = "dc2e6bd1-8156-4886-adff-b39e6043af0c",
            title = "Spirited Away",
            originalTitleRomanised =  "Sen to Chihiro no kamikakushi",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            movieBanner =  "https://image.tmdb.org/t/p/original/bSXfU4dwZyBA1vMmXvejdRXBvuF.jpg",
            description =  "Spirited Away is an Oscar winning Japanese animated film about a ten year old girl who wanders away from her parents along a path that leads to a world ruled by strange and unusual monster-like animals. Her parents have been changed into pigs along with others inside a bathhouse full of these creatures. Will she ever see the world how it once was?",
            director =  "Hayao Miyazaki",
            producer =  "Toshio Suzuki",
            releaseDate =  2001,
            runningTime =  "124",
            rtScore =  97,
            favourite = true
        ),
        Movie(
            id =  "0440483e-ca0e-4120-8c50-4c8cd9b965d6",
            title =  "Princess Mononoke",
            originalTitleRomanised =  "Mononoke hime",
            image =  "https://image.tmdb.org/t/p/w600_and_h900_bestv2/jHWmNr7m544fJ8eItsfNk8fs2Ed.jpg",
            movieBanner = "https://image.tmdb.org/t/p/original/6pTqSq0zYIWCsucJys8q5L92kUY.jpg",
            description =  "Ashitaka, a prince of the disappearing Ainu tribe, is cursed by a demonized boar god and must journey to the west to find a cure. Along the way, he encounters San, a young human woman fighting to protect the forest, and Lady Eboshi, who is trying to destroy it. Ashitaka must find a way to bring balance to this conflict.",
            director =  "Hayao Miyazaki",
            producer =  "Toshio Suzuki",
            releaseDate =  1997,
            runningTime =  "134",
            rtScore =  92,
            favourite = true
        ),
        Movie(
            id =  "58611129-2dbc-4a81-a72f-77ddfc1b1b49",
            title =  "My Neighbor Totoro",
            originalTitleRomanised =  "Tonari no Totoro",
            image =  "https://image.tmdb.org/t/p/w600_and_h900_bestv2/rtGDOeG9LzoerkDGZF9dnVeLppL.jpg",
            movieBanner =  "https://image.tmdb.org/t/p/original/etqr6fOOCXQOgwrQXaKwenTSuzx.jpg",
            description =  "Two sisters move to the country with their father in order to be closer to their hospitalized mother, and discover the surrounding trees are inhabited by Totoros, magical spirits of the forest. When the youngest runs away from home, the older sister seeks help from the spirits to find her.",
            director = "Hayao Miyazaki",
            producer =  "Hayao Miyazaki",
            releaseDate =  1988,
            runningTime =  "86",
            rtScore =  93,
            favourite = false
        ),
        Movie(
            id =  "758bf02e-3122-46e0-884e-67cf83df1786",
            title =  "Ponyo",
            originalTitleRomanised =  "Gake no ue no Ponyo",
            image =  "https://image.tmdb.org/t/p/w600_and_h900_bestv2/mikKSEdk5kLhflWXbp4S5mmHsDo.jpg",
            movieBanner =  "https://image.tmdb.org/t/p/original/6a1qZ1qat26mAIK3Lq8iYdGpyHm.jpg",
            description =  "The son of a sailor, 5-year old Sosuke lives a quiet life on an oceanside cliff with his mother Lisa. One fateful day, he finds a beautiful goldfish trapped in a bottle on the beach and upon rescuing her, names her Ponyo. But she is no ordinary goldfish. The daughter of a masterful wizard and a sea goddess, Ponyo uses her father's magic to transform herself into a young girl and quickly falls in love with Sosuke, but the use of such powerful sorcery causes a dangerous imbalance in the world. As the moon steadily draws nearer to the earth and Ponyo's father sends the ocean's mighty waves to find his daughter, the two children embark on an adventure of a lifetime to save the world and fulfill Ponyo's dreams of becoming human.",
            director =  "Hayao Miyazaki",
            producer =  "Toshio Suzuki",
            releaseDate =  2008,
            runningTime =  "100",
            rtScore =  92,
            favourite = true
        )
    )

    override suspend fun get(): Flow<List<Movie>> {
        delay(1000)
        return flowOf(movies)
    }

    override suspend fun update(movie: Movie): Result<Boolean> {
        delay(1000)
        val index = movies.indexOf(movie)
        movies.remove(movie)
        movies.add(index, movie)
        return Result.success(true)
    }

}