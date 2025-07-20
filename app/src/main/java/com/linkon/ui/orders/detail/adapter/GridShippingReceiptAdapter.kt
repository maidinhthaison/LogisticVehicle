package com.linkon.ui.orders.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linkon.databinding.ViewHolderShippingReceiptItemBinding
import com.linkon.domain.model.order.detail.ShippingReceiptImgModel
import com.linkon.utils.loadImageFitCenter

class GridShippingReceiptAdapter : ListAdapter<ShippingReceiptImgModel, GridShippingReceiptAdapter.ItemViewHolder>(
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
        fun bind(item: ShippingReceiptImgModel) {
            loadImageFitCenter(binding.root.context, item.imgUrl, binding.ivShippingReceiptItem)
        }
    }
    object ItemDiffCallback : DiffUtil.ItemCallback<ShippingReceiptImgModel>() {
        override fun areItemsTheSame(oldItem: ShippingReceiptImgModel, newItem: ShippingReceiptImgModel): Boolean {
            return oldItem.imgUrl == newItem.imgUrl
        }

        override fun areContentsTheSame(oldItem: ShippingReceiptImgModel, newItem: ShippingReceiptImgModel): Boolean {
            return oldItem == newItem
        }
    }
}
