package io.kikiriki.sgmovie.di

import io.kikiriki.sgmovie.data.model.MovieFirestore
import io.kikiriki.sgmovie.data.repository.movie.firestore.MovieFirestoreDataSource
import io.kikiriki.sgmovie.utils.EspressoIdleResource
import javax.inject.Inject

class FakeMovieFirestoreDataSourceImpl @Inject constructor() : MovieFirestoreDataSource {

    private val firestoreMovies = listOf(
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

    override suspend fun get(): List<MovieFirestore> {
        // return the desired result
        return try {
            EspressoIdleResource.increment()
            firestoreMovies
        } finally {
            EspressoIdleResource.decrement()
        }
    }
}