package io.kikiriki.sgmovie.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.core.view.isVisible
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import coil.load
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.databinding.ActivityMainBinding
import io.kikiriki.sgmovie.domain.model.Movie
import io.kikiriki.sgmovie.model.Sort
import io.kikiriki.sgmovie.ui.BaseActivity
import io.kikiriki.sgmovie.ui.adapter.AdapterMovie
import io.kikiriki.sgmovie.ui.movie_detail.MovieDetailFragment
import io.kikiriki.sgmovie.ui.settings.SettingsActivity
import io.kikiriki.sgmovie.utils.CoilUtils
import io.kikiriki.sgmovie.utils.extension.getVersionCode
import io.kikiriki.sgmovie.workers.FirebaseSyncWorker
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @Inject lateinit var viewModel: MainViewModel

    private var selectedSortType: Sort = Sort.NAME
    private val adapter = AdapterMovie()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(viewBinding.root)
        setupObservers()
        setupView()

        viewModel.getAppConfig()
    }

    override fun onStart() {
        super.onStart()

        val workRequest = OneTimeWorkRequestBuilder<FirebaseSyncWorker>()
            .addTag(FirebaseSyncWorker.WORKER_NAME)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniqueWork(FirebaseSyncWorker.WORKER_NAME, ExistingWorkPolicy.KEEP, workRequest)
    }

    override fun onPause() {
        super.onPause()
        WorkManager.getInstance(this)
            .cancelAllWorkByTag(FirebaseSyncWorker.WORKER_NAME)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                openSortMoviesDialog()
                true
            }
            R.id.action_settings -> {
                openSettingsActivity()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupView() {

        setSupportActionBar(viewBinding.toolbar)

        // load default animation for error, loading, or empty state
        val imageLoaderGif = CoilUtils.getImageLoaderGif(this)
        viewBinding.loadingView.imgLoading.load(R.drawable.gif_loading, imageLoaderGif)
        viewBinding.emptyView.imgEmpty.load(R.drawable.gif_empty, imageLoaderGif)
        viewBinding.errorView.imgError.load(R.drawable.gif_error, imageLoaderGif)

        // retry request on fails
        viewBinding.errorView.btnRetry.setOnClickListener { viewModel.getMovies() }

        // recycler view adapter and item click
        viewBinding.recyclerView.adapter = adapter
        adapter.onMovieClick = { movie -> openMovieDetail(movie) }
        adapter.onMovieFavouriteClick = { movie ->
            viewModel.updateMovie(movie)
            viewModel.onClickMovieLike(movie)
        }
    }

    private fun setupObservers() {

        viewModel.appConfig.observe(this@MainActivity) { appConfig ->

            // first check if it a min version available
            if (appConfig.minAppVersion > getVersionCode()) {
                showUpdateDialog()
                return@observe
            } else if (appConfig.isMaintenance) {
                // then, check if it is maintenance
                showMaintenanceDialog()
                return@observe
            }

            viewModel.getMovies()
        }


        // ui state
        viewModel.uiState.observe(this@MainActivity) { uiState ->

            // list items
            viewBinding.recyclerView.isVisible = (uiState.error == null && !uiState.isLoading)
            sortMovies(uiState.items)

            // error view
            viewBinding.errorView.root.isVisible = uiState.error != null
            uiState.error?.let { viewBinding.errorView.lblMessage.setText(uiState.error) }

            // message view
            uiState.message?.let {
                val snackBar = Snackbar.make(viewBinding.root, it, Snackbar.LENGTH_INDEFINITE)
                snackBar.setAction(R.string.retry) { viewModel.getMovies() }
                snackBar.show()
            }

            // loading view
            viewBinding.loadingView.root.isVisible = uiState.isLoading

            // empty view
            viewBinding.emptyView.root.isVisible = (uiState.error == null && !uiState.isLoading && uiState.items.isEmpty())
        }
    }

    private fun openMovieDetail(movie: Movie) {
        MovieDetailFragment
            .newInstance(movie.id)
            .show(supportFragmentManager, MovieDetailFragment::class.simpleName)
    }

    private fun showUpdateDialog() {
        MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialogStyle)
            .setTitle(R.string.dialog_update_title)
            .setMessage(R.string.dialog_update_message)
            .setCancelable(false)
            .setPositiveButton(R.string.dialog_update_btn_update) { _, _ ->
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                } catch (e: Exception) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
                }
                finish()
            }
            .show()
    }

    private fun showMaintenanceDialog() {
        MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialogStyle)
            .setTitle(R.string.dialog_maintenance_title)
            .setMessage(R.string.dialog_maintenance_message)
            .setCancelable(false)
            .show()
    }

    private fun openSortMoviesDialog() {
        viewModel.onSortDialogOpened()

        // get all sorted options
        val sortOptions: List<Sort> = Sort.getAll()
        // find the index of selected sort type
        val sortSelectedIndex = sortOptions.indexOf(selectedSortType)
        // get the strings for each sorted option
        val sortOptionsLabels = sortOptions.map { getString(it.title) }.toTypedArray()

        // show dialog
        MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialogStyle)
            .setTitle(R.string.dialog_sort_by_lbl_title)
            .setIcon(R.drawable.ic_sort)
            .setOnCancelListener { viewModel.onSortDialogCancel() }
            .setNegativeButton(android.R.string.cancel) { _, _ -> viewModel.onSortDialogCancel() }
            .setSingleChoiceItems(sortOptionsLabels, sortSelectedIndex) { dialog, which ->

                // set the selected sorted options and update ui
                sortOptions.getOrNull(which)?.let { newSortTypeSelected ->
                    viewModel.onSortDialogOptionSelected(newSortTypeSelected)
                    selectedSortType = newSortTypeSelected
                    sortMovies(adapter.currentList)
                    updateBadgeSortMenu()
                }

                // dismiss dialog
                dialog.dismiss()
            }
            .show()
    }

    private fun sortMovies(items: List<Movie>) {
        // we need to create a new mutable list
        val newList = mutableListOf<Movie>().apply { addAll(items) }

        when (selectedSortType.type) {
            Sort.Type.LIKE -> { newList.sortByDescending { it.like } }
            Sort.Type.LIKE_COUNT -> { newList.sortByDescending { it.likeCount } }
            Sort.Type.NAME -> { newList.sortBy { it.title } }
            Sort.Type.SCORE -> { newList.sortByDescending { it.rtScore } }
            Sort.Type.DIRECTOR -> { newList.sortBy { it.director } }
            Sort.Type.YEAR -> { newList.sortByDescending { it.releaseDate} }
        }

        adapter.submitList(newList)
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun updateBadgeSortMenu() {
        // get the current badge and remove it
        val badge: BadgeDrawable? = viewBinding.toolbar.tag as? BadgeDrawable
        BadgeUtils.detachBadgeDrawable(badge, viewBinding.toolbar, R.id.action_sort)

        // show badge if selected sort type is not name (Default)
        if(selectedSortType != Sort.NAME) {
            val newBadge = BadgeDrawable.create(this)
            BadgeUtils.attachBadgeDrawable(newBadge, viewBinding.toolbar, R.id.action_sort)
            viewBinding.toolbar.tag = newBadge
        }
    }

    private fun openSettingsActivity() {
        startActivity(
            Intent(this, SettingsActivity::class.java)
        )
    }

}