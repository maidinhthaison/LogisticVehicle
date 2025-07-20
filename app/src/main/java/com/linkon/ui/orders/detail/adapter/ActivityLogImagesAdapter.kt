package com.linkon.ui.orders.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linkon.databinding.ViewHolderShippingReceiptItemBinding
import com.linkon.domain.model.order.detail.ActivityLogImageModel
import com.linkon.utils.loadImageCenterCrop

class ActivityLogImagesAdapter : ListAdapter<ActivityLogImageModel, ActivityLogImagesAdapter.ItemViewHolder>(
    ItemDiffCallback
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ViewHolderShippingReceiptItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ViewHolderShippingReceiptItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ActivityLogImageModel) {
            binding.apply {
                loadImageCenterCrop(binding.root.context, item.imgUrl, binding.ivShippingReceiptItem)
            }
        }
    }
    object ItemDiffCallback : DiffUtil.ItemCallback<ActivityLogImageModel>() {
        override fun areItemsTheSame(oldItem: ActivityLogImageModel, newItem: ActivityLogImageModel): Boolean {
            return oldItem.imgUrl == newItem.imgUrl
        }

        override fun areContentsTheSame(oldItem: ActivityLogImageModel, newItem: ActivityLogImageModel): Boolean {
            return oldItem == newItem
        }
    }
}
