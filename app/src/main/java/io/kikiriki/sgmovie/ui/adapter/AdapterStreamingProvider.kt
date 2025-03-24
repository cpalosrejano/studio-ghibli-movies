package io.kikiriki.sgmovie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import io.kikiriki.sgmovie.databinding.ItemStreamingProviderBinding
import io.kikiriki.sgmovie.domain.model.StreamingProvider
import io.kikiriki.sgmovie.utils.Constants.Coil.CROSSFADE
import io.kikiriki.sgmovie.utils.Constants.Coil.ROUNDED_CORNERS_XS

class AdapterStreamingProvider : ListAdapter<StreamingProvider,
        AdapterStreamingProvider.ViewHolderStreamingProvider>(diffUtil) {

    var onItemClick: ((StreamingProvider) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolderStreamingProvider {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemStreamingProviderBinding.inflate(inflater, parent, false)
        return ViewHolderStreamingProvider(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderStreamingProvider, position: Int) {
        val item = getItem(position)

        // setup views
        holder.viewBinding.imgStreamingProviderName.text = item.name
        holder.viewBinding.imgStreamingProviderImage.load(item.logo) {
            transformations(RoundedCornersTransformation(ROUNDED_CORNERS_XS))
            crossfade(CROSSFADE)
        }

        // setup clicks
        holder.viewBinding.root.setOnClickListener { onItemClick?.invoke(item) }
    }

    class ViewHolderStreamingProvider(val viewBinding: ItemStreamingProviderBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<StreamingProvider>() {
            override fun areItemsTheSame(oldItem: StreamingProvider, newItem: StreamingProvider) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: StreamingProvider, newItem: StreamingProvider) = oldItem.id == newItem.id
        }
    }
}