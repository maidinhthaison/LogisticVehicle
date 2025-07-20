package com.linkon.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linkon.databinding.ViewHolderSelectDriverItemBinding
import com.linkon.domain.model.driver.DriverModel

internal class SelectDriverAdapter  (
    private var context: Context,
) : ListAdapter<DriverModel, SelectDriverAdapter.OrderDetailItemViewHolder>(
    DIFF_CALLBACK
) {

    var onClicked: ((ListDriverUIEvent) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectDriverAdapter.OrderDetailItemViewHolder {
        val binding = ViewHolderSelectDriverItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return OrderDetailItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SelectDriverAdapter.OrderDetailItemViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item, position)

    }

    internal inner class OrderDetailItemViewHolder(private val binding: ViewHolderSelectDriverItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("StringFormatMatches")
        fun bind(item: DriverModel, position: Int) {
            binding.apply {
                tvDriverId.text = item.id
                tvDriverLocation.text = item.name
                root.setOnClickListener {
                    onClicked?.invoke(ListDriverUIEvent.OnItemClicked(item))
                }
            }

        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DriverModel>() {
            override fun areItemsTheSame(oldItem: DriverModel, newItem: DriverModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DriverModel, newItem: DriverModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

sealed class ListDriverUIEvent {
    data class OnItemClicked(val driverModel: DriverModel) : ListDriverUIEvent()
}
