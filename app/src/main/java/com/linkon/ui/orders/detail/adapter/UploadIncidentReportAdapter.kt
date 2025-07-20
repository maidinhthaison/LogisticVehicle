package com.linkon.ui.orders.detail.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linkon.databinding.ViewHolderUploadItemBinding
import com.linkon.databinding.ViewHolderUploadItemButtonBinding
import com.linkon.utils.loadImageShippingReceiptFromUri

class UploadIncidentReportAdapter (
    private val plusClickedCallBack: () -> Unit,
    private val xClosedClickedCallBack: (name : String) -> Unit
) : ListAdapter<GridItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_ADD = 1
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GridItem>() {
            override fun areItemsTheSame(oldItem: GridItem, newItem: GridItem): Boolean {
                return when {
                    oldItem is GridItem.UploadedItem && newItem is GridItem.UploadedItem ->
                        oldItem.imageName == newItem.imageName // Use a unique ID here in a real app
                    oldItem is GridItem.AddAction && newItem is GridItem.AddAction -> true
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: GridItem, newItem: GridItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ItemViewHolder(private val binding: ViewHolderUploadItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GridItem.UploadedItem) {
            loadImageShippingReceiptFromUri(
                binding.root.context,
                item.imageUri,
                binding.ivUploadItem
            )
            binding.ivClose.setOnClickListener {
                xClosedClickedCallBack(item.imageName)
            }
        }
    }

    inner class AddViewHolder(private val binding: ViewHolderUploadItemButtonBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.llUpload.setOnClickListener {
                plusClickedCallBack()
            }
        }
    }

    // getItemViewType implementation remains the same
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is GridItem.UploadedItem -> TYPE_ITEM
            is GridItem.AddAction -> TYPE_ADD
        }
    }

    // onCreateViewHolder implementation remains the same
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                val binding = ViewHolderUploadItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ItemViewHolder(binding = binding)
            }
            TYPE_ADD -> {
                val binding = ViewHolderUploadItemButtonBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                AddViewHolder(binding = binding)

            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    // onBindViewHolder is simplified
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is ItemViewHolder && item is GridItem.UploadedItem) {
            holder.bind(item)

        }else if (holder is AddViewHolder && item is GridItem.AddAction){
            holder.bind()
        }
        // No data to bind for AddViewHolder
    }
}

