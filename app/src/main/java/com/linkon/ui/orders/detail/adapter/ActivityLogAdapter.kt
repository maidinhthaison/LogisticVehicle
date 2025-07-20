package com.linkon.ui.orders.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linkon.R
import com.linkon.databinding.ViewHolderActivityLogItemBinding
import com.linkon.domain.model.order.detail.ActivityLogModel
import com.linkon.utils.DATE_TIME_ACTIVITY_LOG_FORMAT
import com.linkon.utils.DATE_TIME_ACTIVITY_LOG_SERVER_FORMAT
import com.linkon.utils.formatDateTimeServer

class ActivityLogAdapter : ListAdapter<ActivityLogModel, ActivityLogAdapter.ItemViewHolder>(
    ItemDiffCallback
) {
    private lateinit var activityLogImagesAdapter: ActivityLogImagesAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ViewHolderActivityLogItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ViewHolderActivityLogItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ActivityLogModel) {
            binding.apply {
                textViewTimestamp.text = item.logTime?.let {
                    formatDateTimeServer(
                        it, DATE_TIME_ACTIVITY_LOG_SERVER_FORMAT,
                        DATE_TIME_ACTIVITY_LOG_FORMAT)
                }
                textViewStatus.text = item.action
                // tvDetailsLabel.text = item.description

                // Set up the child RecyclerView
                activityLogImagesAdapter = ActivityLogImagesAdapter()
                rvActivityLogImages.apply {
                    layoutManager = GridLayoutManager(root.context, 2)
                    addItemDecoration(GridSpacingItemDecoration(2,
                        root.context.resources.getDimensionPixelSize(R.dimen._8dp), false))
                    adapter = activityLogImagesAdapter
                }
                activityLogImagesAdapter.submitList(item.images)

            }

        }
    }
    object ItemDiffCallback : DiffUtil.ItemCallback<ActivityLogModel>() {
        override fun areItemsTheSame(oldItem: ActivityLogModel, newItem: ActivityLogModel): Boolean {
            return oldItem.logTime == newItem.logTime
        }

        override fun areContentsTheSame(oldItem: ActivityLogModel, newItem: ActivityLogModel): Boolean {
            return oldItem == newItem
        }
    }
}
