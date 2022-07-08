package dev.weazyexe.wretches.ui.newcrime.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.weazyexe.wretches.databinding.ItemPhotoBinding

/**
 * Адаптер для списка фотографий
 */
class PhotoAdapter(
    private val onPhotoClick: ((photo: Uri) -> Unit)? = null,
    private val onCloseClick: ((photo: Uri) -> Unit)? = null
) : ListAdapter<Uri, PhotoAdapter.Holder>(DiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class Holder(
        private val binding: ItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Uri) = with(binding) {
            root.setOnClickListener { onPhotoClick?.invoke(photo) }
            closeBtn.isVisible = onCloseClick != null
            closeBtn.setOnClickListener { onCloseClick?.invoke(photo) }
            Glide.with(itemView.context)
                .load(photo)
                .centerCrop()
                .into(photoIv)
        }
    }

    private class DiffUtils : DiffUtil.ItemCallback<Uri>() {

        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean =
            oldItem == newItem
    }
}