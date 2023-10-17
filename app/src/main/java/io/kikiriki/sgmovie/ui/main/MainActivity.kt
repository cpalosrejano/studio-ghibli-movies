package io.kikiriki.sgmovie.ui.main

import android.os.Bundle
import androidx.core.view.isVisible
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.databinding.ActivityMainBinding
import io.kikiriki.sgmovie.framework.coil.CoilUtils
import io.kikiriki.sgmovie.ui.BaseActivity
import io.kikiriki.sgmovie.ui.adapter.AdapterMovie
import io.kikiriki.sgmovie.ui.movie_detail.MovieDetailFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @Inject lateinit var viewModel: MainViewModel
    private val adapter = AdapterMovie()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setupObservers()
        setupView()

        viewModel.getMovies()
    }

    private fun setupView() {

        // load default animation for error, loading, or empty state
        val imageLoaderGif = CoilUtils.getImageLoaderGif(this)
        viewBinding.loadingView.imgLoading.load(R.drawable.gif_loading, imageLoaderGif)
        viewBinding.emptyView.imgEmpty.load(R.drawable.gif_empty, imageLoaderGif)
        viewBinding.errorView.imgError.load(R.drawable.gif_error, imageLoaderGif)

        // retry request on fails
        viewBinding.errorView.btnRetry.setOnClickListener {
            viewModel.getMovies()
        }

        // recycler view adapter and item click
        viewBinding.recyclerView.adapter = adapter
        adapter.onMovieClick = { movie -> openMovieDetail(movie) }
        adapter.onMovieFavouriteClick = { movie -> viewModel.updateMovie(movie) }
    }

    private fun setupObservers() {

        // ui state
        viewModel.uiState.observe(this@MainActivity) { uiState ->

            // list items
            viewBinding.recyclerView.isVisible = (uiState.error == null && !uiState.isLoading)
            adapter.submitList(uiState.items)

            // error view
            viewBinding.errorView.root.isVisible = uiState.error != null
            uiState.error?.let { viewBinding.errorView.lblMessage.setText(uiState.error) }

            // loading view
            viewBinding.loadingView.root.isVisible = uiState.isLoading

            // empty view
            viewBinding.emptyView.root.isVisible = (uiState.error == null && !uiState.isLoading && uiState.items.isEmpty())
        }
    }

    private fun openMovieDetail(movie: Movie) {
        MovieDetailFragment
            .newInstance(movie)
            .show(supportFragmentManager, MovieDetailFragment::class.simpleName)
    }

}