package io.kikiriki.sgmovie.ui.main

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.core.view.isVisible
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.databinding.ActivityMainBinding
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

        viewModel.getNotes()
    }

    private fun setupView() {

        // custom image loader to load gif images
        val imageLoader = ImageLoader.Builder(this)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        viewBinding.loading.load(R.drawable.ic_error, imageLoader)

        // click try again
        viewBinding.btnTryAgain.setOnClickListener { viewModel.getNotes() }

        // recycler view adapter and item click
        viewBinding.recyclerView.adapter = adapter
        adapter.onItemClick = { shortToast(R.string.app_name) }
    }

    private fun setupObservers() = viewModel.uiState.observe(this) { uiState ->

        // loading
        viewBinding.loading.isVisible = uiState.isLoading

        // items
        viewBinding.recyclerView.isVisible = !uiState.isLoading
        adapter.submitList(uiState.items)

        // error
        uiState.error?.let { toast(it) }

    }


}