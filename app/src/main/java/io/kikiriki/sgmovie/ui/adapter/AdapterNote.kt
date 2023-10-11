package io.kikiriki.sgmovie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.kikiriki.sgmovie.databinding.ItemNoteBinding
import io.kikiriki.sgmovie.data.model.domain.Note
import io.kikiriki.sgmovie.utils.DateFormat
import io.kikiriki.sgmovie.utils.extension.firstPhrase
import io.kikiriki.sgmovie.utils.extension.format

class AdapterNote : ListAdapter<Note, AdapterNote.ViewHolderNote>(diffUtil) {

    var onItemClick: ((Note) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolderNote {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemNoteBinding.inflate(inflater, parent, false)
        return ViewHolderNote(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderNote, position: Int) {
        val note = getItem(position)

        holder.viewBinding.day.text = note.createdAt.format(DateFormat.DAY_OF_WEEK_AND_DAY)
        holder.viewBinding.month.text = note.createdAt.format(DateFormat.SHORT_MONTH)
        holder.viewBinding.title.text = note.text?.firstPhrase()
        holder.viewBinding.text.text = note.text

        holder.viewBinding.root.setOnClickListener { onItemClick?.invoke(note) }
    }

    class ViewHolderNote(val viewBinding: ItemNoteBinding) : RecyclerView.ViewHolder(viewBinding.root)

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
        }
    }
}