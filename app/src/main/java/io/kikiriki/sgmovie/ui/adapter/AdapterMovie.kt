package io.kikiriki.sgmovie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.data.model.domain.Movie
import io.kikiriki.sgmovie.databinding.ItemMovieBinding
import io.kikiriki.sgmovie.utils.Constants.Coil.CROSSFADE
import io.kikiriki.sgmovie.utils.Constants.Coil.ROUNDED_CORNERS

class AdapterMovie : ListAdapter<Movie, AdapterMovie.ViewHolderNote>(diffUtil) {

    var onMovieClick: ((Movie) -> Unit)? = null
    var onMovieFavouriteClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolderNote {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemMovieBinding.inflate(inflater, parent, false)
        return ViewHolderNote(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderNote, position: Int) {
        val item = getItem(position)

        // setup views
        holder.viewBinding.lblTitle.text = item.title
        holder.viewBinding.lblDescription.text = item.description
        holder.viewBinding.imgImage.load(item.image) {
            transformations(RoundedCornersTransformation(ROUNDED_CORNERS))
            crossfade(CROSSFADE)
        }
        holder.viewBinding.imgSave.load(
            if (item.favourite) R.drawable.ic_saved else R.drawable.ic_save
        )

        // setup clicks
        holder.viewBinding.imgSave.setOnClickListener { onMovieFavouriteClick?.invoke(item) }
        holder.viewBinding.root.setOnClickListener { onMovieClick?.invoke(item) }
    }

    class ViewHolderNote(val viewBinding: ItemMovieBinding) : RecyclerView.ViewHolder(viewBinding.root)

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem.favourite == newItem.favourite
        }
    }
}