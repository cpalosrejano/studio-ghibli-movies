package io.kikiriki.sgmovie.ui.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.databinding.FragmentMovieDetailBinding
import io.kikiriki.sgmovie.utils.Constants.Coil.CROSSFADE
import io.kikiriki.sgmovie.utils.Constants.Coil.ROUNDED_CORNERS
import io.kikiriki.sgmovie.utils.Constants.Coil.ROUNDED_CORNERS_XL
import io.kikiriki.sgmovie.utils.extension.getParcelableSupport
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment private constructor() : BottomSheetDialogFragment() {

    private val viewBinding by lazy { FragmentMovieDetailBinding.inflate(layoutInflater) }
    @Inject lateinit var viewModel: MovieDetailViewModel

    private var movie: Movie? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        movie = arguments?.getParcelableSupport(EXTRA_MOVIE, Movie::class.java)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupView()
    }

    private fun setupView() {
        movie?.let { movie ->
            viewBinding.imgBanner.load(movie.movieBanner) {
                transformations(RoundedCornersTransformation(topLeft = ROUNDED_CORNERS_XL, topRight = ROUNDED_CORNERS_XL))
                crossfade(CROSSFADE)
            }
            viewBinding.imgImage.load(movie.image) {
                transformations(RoundedCornersTransformation(ROUNDED_CORNERS))
                crossfade(CROSSFADE)
            }
            viewBinding.lblTitle.text = movie.title
            viewBinding.lblDirector.text = movie.director
            viewBinding.lblYear.text = movie.releaseDate.toString()
            viewBinding.lblRunningTime.text = getString(R.string.movie_detail_lbl_running_time, movie.runningTime)
            viewBinding.lblScore.text = String.format(getString(R.string.movie_detail_lbl_score), movie.rtScore)
            viewBinding.lblDescription.text = movie.description
            viewBinding.lblFavourite.setOnClickListener { viewModel.updateMovie(movie) }
            val iconFavourite = if(movie.favourite) R.drawable.ic_saved else R.drawable.ic_save
            viewBinding.lblFavourite.setCompoundDrawablesWithIntrinsicBounds(0, iconFavourite,0,0)

        }
    }

    private fun setupObserver() = viewModel.uiState.observe(this) { uiState ->

        // update movie and setup view
        uiState.movie?.let {
            movie = it
            setupView()
        }

        // error view
        uiState.error?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
    }

    companion object {
        private const val EXTRA_MOVIE = "extra-movie"

        fun newInstance(movie: Movie) : MovieDetailFragment {
            val fragment = MovieDetailFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(EXTRA_MOVIE, movie)
            }
            return fragment
        }
    }

}