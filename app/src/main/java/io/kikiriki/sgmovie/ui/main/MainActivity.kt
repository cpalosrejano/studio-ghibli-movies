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
import io.kikiriki.sgmovie.utils.extension.shortToast
import io.kikiriki.sgmovie.utils.extension.toast
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

        // custom image loader to load gif images
        viewBinding.loading.load(R.drawable.ic_loading, CoilUtils.getImageLoaderGif(this))


        // recycler view adapter and item click
        viewBinding.recyclerView.adapter = adapter
        adapter.onItemClick = { shortToast(R.string.app_name) }
        adapter.onItemSaveClick = { position, movie -> onMovieSaveClick(position, movie) }
    }

    private fun setupObservers() = viewModel.uiState.observe(this) { uiState ->

        when(uiState) {
            // when movies has been fetched from API
            is MainUIState.OnMoviesReady -> {
                adapter.submitList(uiState.items)
                viewBinding.recyclerView.isVisible = uiState.items.isNotEmpty()
            }
            // when movie has been updated in our database
            is MainUIState.OnMovieUpdated -> {
                adapter.notifyItemChanged(uiState.position)
            }
        }

        // error and loading
        uiState.error?.let { toast(it) }
        viewBinding.loading.isVisible = uiState.isLoading
    }

    private fun onMovieSaveClick(listPosition: Int, movie: Movie) {
        movie.favourite = !movie.favourite
        viewModel.updateMovieFavourite(listPosition, movie)
    }


}