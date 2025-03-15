package io.kikiriki.sgmovie.ui.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.databinding.FragmentMovieDetailBinding
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.utils.Constants.Coil.CROSSFADE
import io.kikiriki.sgmovie.utils.Constants.Coil.ROUNDED_CORNERS
import io.kikiriki.sgmovie.utils.Constants.Coil.ROUNDED_CORNERS_XL
import io.kikiriki.sgmovie.utils.extension.getParcelableSupport
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : BottomSheetDialogFragment() {

    private val viewBinding by lazy { FragmentMovieDetailBinding.inflate(layoutInflater) }
    @Inject lateinit var viewModel: MovieDetailViewModel

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics

        val movieId = arguments?.getString(EXTRA_MOVIE_ID).orEmpty()
        viewModel.getMovieById(movieId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupView()
    }

    private fun setupView() {
        viewBinding.lblLike.setOnClickListener {
            viewModel.updateMovieLike()
        }
    }

    private fun setupObserver() {

        // show possible errors
        viewModel.error.observe(this) { error: Int? ->
            error?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        }

        // print movie updates
        viewModel.movie.observe(viewLifecycleOwner) { movie: Movie ->
            updateMovieDetail(movie)
        }

    }

    private fun updateMovieDetail(movie: Movie) {
        viewBinding.imgBanner.load(movie.imageBanner) {
            transformations(RoundedCornersTransformation(topLeft = ROUNDED_CORNERS_XL, topRight = ROUNDED_CORNERS_XL))
            crossfade(CROSSFADE)
        }
        viewBinding.imgImage.load(movie.imageCartel) {
            transformations(RoundedCornersTransformation(ROUNDED_CORNERS))
            crossfade(CROSSFADE)
        }
        viewBinding.lblTitle.text = movie.title
        viewBinding.lblDirector.text = movie.director
        viewBinding.lblYear.text = movie.releaseDate.toString()
        val hours = movie.runningTime / 60
        val minutes = String.format("%02d", movie.runningTime % 60)
        if (hours >= 1) {
            viewBinding.lblRunningTime.text = getString(R.string.movie_detail_lbl_running_time_hour_min, hours, minutes)
        } else {
            viewBinding.lblRunningTime.text = getString(R.string.movie_detail_lbl_running_time_min, minutes)
        }
        viewBinding.lblScore.text = String.format(getString(R.string.movie_detail_lbl_score), movie.rtScore)
        viewBinding.lblDescription.text = movie.description

        val iconFavourite = if(movie.like) R.drawable.ic_like_filled else R.drawable.ic_like
        viewBinding.lblLike.setCompoundDrawablesWithIntrinsicBounds(0, iconFavourite,0,0)
        viewBinding.lblLike.text = getString(R.string.movie_detail_lbl_likes, movie.likeCount)
    }

    private fun sendAnalyticEventAddFavoriteMovie(movie: Movie) {
        firebaseAnalytics.logEvent("add_favorite") {
            param("movie", movie.title)
        }
    }
    private fun sendAnalyticEventDeleteFavoriteMovie(movie: Movie) {
        firebaseAnalytics.logEvent("delete_favorite") {
            param("movie", movie.title)
        }
    }

    companion object {
        private const val EXTRA_MOVIE_ID = "extra-movie-id"

        fun newInstance(movieId: String) : MovieDetailFragment {
            val fragment = MovieDetailFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_MOVIE_ID, movieId)
            }
            return fragment
        }
    }

}