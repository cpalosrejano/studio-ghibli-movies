package io.kikiriki.sgmovie.data.repository.movie.util

import io.kikiriki.sgmovie.data.model.MovieFirestore
import io.kikiriki.sgmovie.data.model.MovieLocal
import io.kikiriki.sgmovie.data.model.MovieRemote
import io.kikiriki.sgmovie.domain.model.Movie

object DataMock {

    val moviesFirestore = listOf<MovieFirestore>(
        MovieFirestore(
            id = "loiy6h9s-1hn3-0ol2-8au2-abskieubsgj3",
            title = "Ocean waves",
            original_title_romanised =  "Umi ga kikoeru",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            movie_banner =  "https://m.media-amazon.com/images/S/pv-target-images/016692f2fed7356122573c2253302f032aa8198235823f4230bf62afd5338ff4.jpg",
            description =  "As a young man returns home after his first year away at college he recalls his senior year of high school and the iron-willed, big city girl that turned his world upside down",
            director =  "Tomomi Mochizuki",
            producer =  "Toshio Suzuki",
            release_year =  1993,
            duration =  76,
            rt_score =  89
        ),
        MovieFirestore(
            id =  "0ajd873a-jsk23-d82a-jk27-0a9s62js84h2",
            title =  "The boy and the heron",
            original_title_romanised =  "Kimitachi wa dô ikiru ka",
            image =  "https://i.ebayimg.com/images/g/fs4AAOSwn-xlQiL4/s-l1600.jpg",
            movie_banner = "https://amplify.nabshow.com/wp-content/uploads/sites/12/2023/12/HERON-feature.png",
            description = "In the wake of his mother's death and his father's remarriage, a headstrong boy named Mahito ventures into a dreamlike world shared by both the living and the dead.",
            director =  "Hayao Miyazaki",
            producer =  "Toshio Suzuki",
            release_year =  2023,
            duration =  124,
            rt_score =  97
        )
    )

    val moviesRemote = listOf<MovieRemote>(
        MovieRemote(
            id = "dc2e6bd1-8156-4886-adff-b39e6043af0c",
            title = "Spirited Away",
            original_title_romanised = "Sen to Chihiro no kamikakushi",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            movie_banner = "https://image.tmdb.org/t/p/original/bSXfU4dwZyBA1vMmXvejdRXBvuF.jpg",
            description = "Spirited Away is an Oscar winning Japanese animated film about a ten year old girl who wanders away from her parents along a path that leads to a world ruled by strange and unusual monster-like animals. Her parents have been changed into pigs along with others inside a bathhouse full of these creatures. Will she ever see the world how it once was?",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            release_date = "2001",
            running_time = "124",
            rt_score = "97"
        ),
        MovieRemote(
            id = "0440483e-ca0e-4120-8c50-4c8cd9b965d6",
            title = "Princess Mononoke",
            original_title_romanised = "Mononoke hime",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/jHWmNr7m544fJ8eItsfNk8fs2Ed.jpg",
            movie_banner = "https://image.tmdb.org/t/p/original/6pTqSq0zYIWCsucJys8q5L92kUY.jpg",
            description = "Ashitaka, a prince of the disappearing Ainu tribe, is cursed by a demonized boar god and must journey to the west to find a cure. Along the way, he encounters San, a young human woman fighting to protect the forest, and Lady Eboshi, who is trying to destroy it. Ashitaka must find a way to bring balance to this conflict.",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            release_date = "1997",
            running_time = "134",
            rt_score = "92"
        ),
        MovieRemote(
            id = "58611129-2dbc-4a81-a72f-77ddfc1b1b49",
            title = "My Neighbor Totoro",
            original_title_romanised = "Tonari no Totoro",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/rtGDOeG9LzoerkDGZF9dnVeLppL.jpg",
            movie_banner = "https://image.tmdb.org/t/p/original/etqr6fOOCXQOgwrQXaKwenTSuzx.jpg",
            description = "Two sisters move to the country with their father in order to be closer to their hospitalized mother, and discover the surrounding trees are inhabited by Totoros, magical spirits of the forest. When the youngest runs away from home, the older sister seeks help from the spirits to find her.",
            director = "Hayao Miyazaki",
            producer = "Hayao Miyazaki",
            release_date = "1988",
            running_time = "86",
            rt_score = "93"
        ),
        MovieRemote(
            id = "758bf02e-3122-46e0-884e-67cf83df1786",
            title = "Ponyo",
            original_title_romanised = "Gake no ue no Ponyo",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/mikKSEdk5kLhflWXbp4S5mmHsDo.jpg",
            movie_banner = "https://image.tmdb.org/t/p/original/6a1qZ1qat26mAIK3Lq8iYdGpyHm.jpg",
            description = "The son of a sailor, 5-year old Sosuke lives a quiet life on an oceanside cliff with his mother Lisa. One fateful day, he finds a beautiful goldfish trapped in a bottle on the beach and upon rescuing her, names her Ponyo. But she is no ordinary goldfish. The daughter of a masterful wizard and a sea goddess, Ponyo uses her father's magic to transform herself into a young girl and quickly falls in love with Sosuke, but the use of such powerful sorcery causes a dangerous imbalance in the world. As the moon steadily draws nearer to the earth and Ponyo's father sends the ocean's mighty waves to find his daughter, the two children embark on an adventure of a lifetime to save the world and fulfill Ponyo's dreams of becoming human.",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            release_date = "2008",
            running_time = "100",
            rt_score = "92"
        )
    )

    val moviesLocal = listOf<MovieLocal>(
        MovieLocal(
            id = "dc2e6bd1-8156-4886-adff-b39e6043af0c",
            title = "Spirited Away",
            original_title_romanised = "Sen to Chihiro no kamikakushi",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            movie_banner = "https://image.tmdb.org/t/p/original/bSXfU4dwZyBA1vMmXvejdRXBvuF.jpg",
            description = "Spirited Away is an Oscar winning Japanese animated film about a ten year old girl who wanders away from her parents along a path that leads to a world ruled by strange and unusual monster-like animals. Her parents have been changed into pigs along with others inside a bathhouse full of these creatures. Will she ever see the world how it once was?",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            release_date = "2001",
            running_time = "124",
            rt_score = "97"
        ),
        MovieLocal(
            id = "0440483e-ca0e-4120-8c50-4c8cd9b965d6",
            title = "Princess Mononoke",
            original_title_romanised = "Mononoke hime",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/jHWmNr7m544fJ8eItsfNk8fs2Ed.jpg",
            movie_banner = "https://image.tmdb.org/t/p/original/6pTqSq0zYIWCsucJys8q5L92kUY.jpg",
            description = "Ashitaka, a prince of the disappearing Ainu tribe, is cursed by a demonized boar god and must journey to the west to find a cure. Along the way, he encounters San, a young human woman fighting to protect the forest, and Lady Eboshi, who is trying to destroy it. Ashitaka must find a way to bring balance to this conflict.",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            release_date = "1997",
            running_time = "134",
            rt_score = "92"
        ),
        MovieLocal(
            id = "58611129-2dbc-4a81-a72f-77ddfc1b1b49",
            title = "My Neighbor Totoro",
            original_title_romanised = "Tonari no Totoro",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/rtGDOeG9LzoerkDGZF9dnVeLppL.jpg",
            movie_banner = "https://image.tmdb.org/t/p/original/etqr6fOOCXQOgwrQXaKwenTSuzx.jpg",
            description = "Two sisters move to the country with their father in order to be closer to their hospitalized mother, and discover the surrounding trees are inhabited by Totoros, magical spirits of the forest. When the youngest runs away from home, the older sister seeks help from the spirits to find her.",
            director = "Hayao Miyazaki",
            producer = "Hayao Miyazaki",
            release_date = "1988",
            running_time = "86",
            rt_score = "93"
        ),
        MovieLocal(
            id = "758bf02e-3122-46e0-884e-67cf83df1786",
            title = "Ponyo",
            original_title_romanised = "Gake no ue no Ponyo",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/mikKSEdk5kLhflWXbp4S5mmHsDo.jpg",
            movie_banner = "https://image.tmdb.org/t/p/original/6a1qZ1qat26mAIK3Lq8iYdGpyHm.jpg",
            description = "The son of a sailor, 5-year old Sosuke lives a quiet life on an oceanside cliff with his mother Lisa. One fateful day, he finds a beautiful goldfish trapped in a bottle on the beach and upon rescuing her, names her Ponyo. But she is no ordinary goldfish. The daughter of a masterful wizard and a sea goddess, Ponyo uses her father's magic to transform herself into a young girl and quickly falls in love with Sosuke, but the use of such powerful sorcery causes a dangerous imbalance in the world. As the moon steadily draws nearer to the earth and Ponyo's father sends the ocean's mighty waves to find his daughter, the two children embark on an adventure of a lifetime to save the world and fulfill Ponyo's dreams of becoming human.",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            release_date = "2008",
            running_time = "100",
            rt_score = "92"
        )
    )

    val movies = listOf<Movie>(
        Movie(
            id = "dc2e6bd1-8156-4886-adff-b39e6043af0c",
            title = "Spirited Away",
            originalTitleRomanised = "Sen to Chihiro no kamikakushi",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            movieBanner = "https://image.tmdb.org/t/p/original/bSXfU4dwZyBA1vMmXvejdRXBvuF.jpg",
            description = "Spirited Away is an Oscar winning Japanese animated film about a ten year old girl who wanders away from her parents along a path that leads to a world ruled by strange and unusual monster-like animals. Her parents have been changed into pigs along with others inside a bathhouse full of these creatures. Will she ever see the world how it once was?",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            releaseDate = 2001,
            runningTime = "124",
            rtScore = 97,
            favourite = true
        ),
        Movie(
            id = "0440483e-ca0e-4120-8c50-4c8cd9b965d6",
            title = "Princess Mononoke",
            originalTitleRomanised = "Mononoke hime",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/jHWmNr7m544fJ8eItsfNk8fs2Ed.jpg",
            movieBanner = "https://image.tmdb.org/t/p/original/6pTqSq0zYIWCsucJys8q5L92kUY.jpg",
            description = "Ashitaka, a prince of the disappearing Ainu tribe, is cursed by a demonized boar god and must journey to the west to find a cure. Along the way, he encounters San, a young human woman fighting to protect the forest, and Lady Eboshi, who is trying to destroy it. Ashitaka must find a way to bring balance to this conflict.",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            releaseDate = 1997,
            runningTime = "134",
            rtScore = 92,
            favourite = true
        ),
        Movie(
            id = "58611129-2dbc-4a81-a72f-77ddfc1b1b49",
            title = "My Neighbor Totoro",
            originalTitleRomanised = "Tonari no Totoro",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/rtGDOeG9LzoerkDGZF9dnVeLppL.jpg",
            movieBanner = "https://image.tmdb.org/t/p/original/etqr6fOOCXQOgwrQXaKwenTSuzx.jpg",
            description = "Two sisters move to the country with their father in order to be closer to their hospitalized mother, and discover the surrounding trees are inhabited by Totoros, magical spirits of the forest. When the youngest runs away from home, the older sister seeks help from the spirits to find her.",
            director = "Hayao Miyazaki",
            producer = "Hayao Miyazaki",
            releaseDate = 1988,
            runningTime = "86",
            rtScore = 93,
            favourite = false
        ),
        Movie(
            id = "758bf02e-3122-46e0-884e-67cf83df1786",
            title = "Ponyo",
            originalTitleRomanised = "Gake no ue no Ponyo",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/mikKSEdk5kLhflWXbp4S5mmHsDo.jpg",
            movieBanner = "https://image.tmdb.org/t/p/original/6a1qZ1qat26mAIK3Lq8iYdGpyHm.jpg",
            description = "The son of a sailor, 5-year old Sosuke lives a quiet life on an oceanside cliff with his mother Lisa. One fateful day, he finds a beautiful goldfish trapped in a bottle on the beach and upon rescuing her, names her Ponyo. But she is no ordinary goldfish. The daughter of a masterful wizard and a sea goddess, Ponyo uses her father's magic to transform herself into a young girl and quickly falls in love with Sosuke, but the use of such powerful sorcery causes a dangerous imbalance in the world. As the moon steadily draws nearer to the earth and Ponyo's father sends the ocean's mighty waves to find his daughter, the two children embark on an adventure of a lifetime to save the world and fulfill Ponyo's dreams of becoming human.",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            releaseDate = 2008,
            runningTime = "100",
            rtScore = 92,
            favourite = true
        )
    )

}