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
import dagger.hilt.android.AndroidEntryPoint
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.databinding.FragmentMovieDetailBinding
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.domain.model.StreamingProvider
import io.kikiriki.sgmovie.ui.adapter.AdapterStreamingProvider
import io.kikiriki.sgmovie.utils.Constants.Coil.CROSSFADE
import io.kikiriki.sgmovie.utils.Constants.Coil.ROUNDED_CORNERS
import io.kikiriki.sgmovie.utils.Constants.Coil.ROUNDED_CORNERS_XL
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : BottomSheetDialogFragment() {

    private val viewBinding by lazy { FragmentMovieDetailBinding.inflate(layoutInflater) }
    @Inject lateinit var viewModel: MovieDetailViewModel

    private val spFlatRateAdapter = AdapterStreamingProvider()
    private val spRentAdapter = AdapterStreamingProvider()
    private val spBuyAdapter = AdapterStreamingProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieId = arguments?.getString(EXTRA_MOVIE_ID).orEmpty()
        viewModel.getMovieById(movieId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.logScreenView()
        setupObserver()
        setupView()
    }

    private fun setupView() {
        viewBinding.recyclerStreamingProvidersFlatRate.adapter = spFlatRateAdapter
        spFlatRateAdapter.onItemClick = { streamingProvider ->
            viewModel.onStreamingProviderSelected(streamingProvider)
            Toast.makeText(context, streamingProvider.name, Toast.LENGTH_SHORT).show()
        }

        viewBinding.recyclerStreamingProvidersRent.adapter = spRentAdapter
        spRentAdapter.onItemClick = { streamingProvider ->
            viewModel.onStreamingProviderSelected(streamingProvider)
            Toast.makeText(context, streamingProvider.name, Toast.LENGTH_SHORT).show()
        }

        viewBinding.recyclerStreamingProvidersBuy.adapter = spBuyAdapter
        spBuyAdapter.onItemClick = { streamingProvider ->
            viewModel.onStreamingProviderSelected(streamingProvider)
            Toast.makeText(context, streamingProvider.name, Toast.LENGTH_SHORT).show()
        }

        viewBinding.lblLike.setOnClickListener {
            viewModel.updateMovieLike()
            viewModel.onClickMovieLike()
        }
    }

    private fun setupObserver() {

        // show possible errors
        viewModel.error.observe(this) { error: Int? ->
            error?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        }

        viewModel.streamingProviderError.observe(viewLifecycleOwner) { error: Int? ->
            error?.let {
                showStreamingProviderError(getString(error))
            }
        }

        // print movie updates
        viewModel.movie.observe(viewLifecycleOwner) { movie: Movie ->
            updateMovieDetail(movie)
        }

        // show streaming providers
        viewModel.streamingProviders.observe(viewLifecycleOwner) { providers ->
            providers?.let {
                // load providers
                loadFlatRateProviders(it.flatrate)
                loadRentProviders(it.rent)
                loadBuyProviders(it.buy)

                // show empty providers message if all of them are empty
                val emptyProviders = it.flatrate.isEmpty() && it.rent.isEmpty() && it.buy.isEmpty()
                if (emptyProviders) {
                    val countryName = Locale.getDefault().displayCountry + " " + Locale.getDefault().country
                    val msg = getString(R.string.movie_detail_lbl_streaming_providers_not_available, countryName)
                    showStreamingProviderError(msg)
                }
            }
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

    private fun loadFlatRateProviders(providers: List<StreamingProvider>) {
        viewBinding.layoutStreamingProvidersFlatRate.isVisible = providers.isNotEmpty()
        spFlatRateAdapter.submitList(providers)
    }

    private fun loadRentProviders(providers: List<StreamingProvider>) {
        viewBinding.layoutStreamingProvidersRent.isVisible = providers.isNotEmpty()
        spRentAdapter.submitList(providers)
    }

    private fun loadBuyProviders(providers: List<StreamingProvider>) {
        viewBinding.layoutStreamingProvidersBuy.isVisible = providers.isNotEmpty()
        spBuyAdapter.submitList(providers)
    }

    private fun showStreamingProviderError(message: String) {
        viewBinding.layoutStreamingProvidersFlatRate.isGone = true
        viewBinding.layoutStreamingProvidersRent.isGone = true
        viewBinding.layoutStreamingProvidersBuy.isGone = true
        viewBinding.lblStreamingProvidersNotAvailable.isVisible = true
        viewBinding.lblStreamingProvidersNotAvailable.text = message
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