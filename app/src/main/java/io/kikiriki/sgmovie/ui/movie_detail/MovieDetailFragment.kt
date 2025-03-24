package io.kikiriki.sgmovie.ui.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
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
import io.kikiriki.sgmovie.domain.model.StreamingProvider
import io.kikiriki.sgmovie.ui.adapter.AdapterStreamingProvider
import io.kikiriki.sgmovie.utils.Constants.Coil.CROSSFADE
import io.kikiriki.sgmovie.utils.Constants.Coil.ROUNDED_CORNERS
import io.kikiriki.sgmovie.utils.Constants.Coil.ROUNDED_CORNERS_XL
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : BottomSheetDialogFragment() {

    private val viewBinding by lazy { FragmentMovieDetailBinding.inflate(layoutInflater) }
    @Inject lateinit var viewModel: MovieDetailViewModel

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val streamingProviderAdapter = AdapterStreamingProvider()

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
        viewBinding.recyclerStreamingProviders.adapter = streamingProviderAdapter
        viewBinding.lblLike.setOnClickListener {
            viewModel.updateMovieLike()
        }
    }

    private fun setupObserver() {

        // show possible errors
        viewModel.error.observe(this) { error: Int? ->
            error?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        }

        viewModel.streamingProviderError.observe(viewLifecycleOwner) { error: Int? ->
            error?.let { showStreamingProviderError(error) }
        }

        // print movie updates
        viewModel.movie.observe(viewLifecycleOwner) { movie: Movie ->
            updateMovieDetail(movie)
        }

        // show streaming providers
        viewModel.streamingProviders.observe(viewLifecycleOwner) { providers ->
            providers?.let { loadStreamingProviders(it) }
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

    private fun loadStreamingProviders(providers: List<StreamingProvider>) {
        if (providers.isEmpty()) {
            viewBinding.lblStreamingProvidersNotAvailable.text =
                getString(R.string.movie_detail_lbl_streaming_provider_not_available)
            viewBinding.lblStreamingProvidersNotAvailable.isVisible = true
            viewBinding.recyclerStreamingProviders.isGone = true
        } else {
            viewBinding.lblStreamingProvidersNotAvailable.isGone = true
            viewBinding.recyclerStreamingProviders.isVisible = true
            streamingProviderAdapter.submitList(providers)
        }
    }

    private fun showStreamingProviderError(message: Int) {
        viewBinding.lblStreamingProvidersNotAvailable.text = getString(message)
        viewBinding.lblStreamingProvidersNotAvailable.isVisible = true
        viewBinding.recyclerStreamingProviders.isGone = true
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