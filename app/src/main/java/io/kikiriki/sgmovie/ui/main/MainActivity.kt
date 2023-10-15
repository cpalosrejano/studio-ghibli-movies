package io.kikiriki.sgmovie.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
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

        requestMovies()
    }

    private fun setupView() {

        // load default animation for error, loading, or empty state
        val imageLoaderGif = CoilUtils.getImageLoaderGif(this)
        viewBinding.loadingView.imgLoading.load(R.drawable.gif_loading, imageLoaderGif)
        viewBinding.emptyView.imgEmpty.load(R.drawable.gif_empty, imageLoaderGif)
        viewBinding.errorView.imgError.load(R.drawable.gif_error, imageLoaderGif)

        // retry request on fails
        viewBinding.errorView.btnRetry.setOnClickListener { requestMovies() }

        // recycler view adapter and item click
        viewBinding.recyclerView.adapter = adapter
        adapter.onItemClick = { position, movie -> openMovieDetail(position, movie) }
        adapter.onItemSaveClick = { position, movie -> onMovieSaveClick(position, movie) }
    }

    private fun setupObservers() = viewModel.uiState.observe(this) { uiState ->

        when(uiState) {
            // when movies has been fetched from API
            is MainUIState.OnMoviesReady -> {
                adapter.submitList(uiState.items)
                viewBinding.recyclerView.isVisible = uiState.items.isNotEmpty()
                viewBinding.emptyView.root.isGone = uiState.items.isNotEmpty()
            }
            // when movie has been updated in our database
            is MainUIState.OnMovieUpdated -> {
                adapter.notifyItemChanged(uiState.position)
            }
        }

        // error view
        viewBinding.errorView.root.isGone = uiState.error == null
        uiState.error?.let { viewBinding.errorView.lblMessage.setText(uiState.error) }

        // loading view
        viewBinding.loadingView.root.isVisible = uiState.isLoading
    }

    private fun requestMovies() {
        viewModel.getMovies()
    }

    private fun onMovieSaveClick(listPosition: Int, movie: Movie) {
        movie.favourite = !movie.favourite
        viewModel.updateMovieFavourite(listPosition, movie)
    }

    private fun openMovieDetail(listPosition: Int, movie: Movie) {
        val dialog = MovieDetailFragment.newInstance(listPosition, movie)
        dialog.show(supportFragmentManager, MovieDetailFragment::class.simpleName)
        dialog.onMovieFavouriteClick = { onMovieSaveClick(listPosition, movie) }

    }


}