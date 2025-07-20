package com.linkon.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linkon.R
import com.linkon.databinding.ViewHolderFactoryOrderItemBinding
import com.linkon.domain.model.order.OrderListItemModel
import com.linkon.utils.formatPriceTo3Digits


class FactoryOrderAdapter : ListAdapter<OrderListItemModel, FactoryOrderAdapter.ItemViewHolder>(
    ItemDiffCallback
) {
    var onClickedItem: ((ListFactoryOrderUIEvent) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ViewHolderFactoryOrderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ViewHolderFactoryOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderListItemModel) {
            binding.apply {
                tvOrderId.text = item.orderNo
                tvCreatedAt.text = String.format(
                    root.context.getString(R.string.order_item_view_created_at),
                    item.createdAt
                )
                tvPrice.text = String.format(
                    root.context.getString(R.string.order_item_view_price),
                    item.transportFee
                )
                /*tvPrice.text = String.format(
                    root.context.getString(R.string.order_item_view_price),
                    formatPriceTo3Digits("999")
                )*/

                tvFactoryName.text = item.factoryName
                tvFactoryAddress.text = item.factoryAddress
                tvFactoryCheckPoint.text = item.receiverAddress
                tvContainerTruckCode.text = item.vehicleNo
                tvTransportDateValue.text = item.transportStartTime
                root.setOnClickListener {
                    onClickedItem?.invoke(ListFactoryOrderUIEvent.OnItemClicked(item))
                }

            }


        }
    }

    object ItemDiffCallback : DiffUtil.ItemCallback<OrderListItemModel>() {
        override fun areItemsTheSame(
            oldItem: OrderListItemModel,
            newItem: OrderListItemModel
        ): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(
            oldItem: OrderListItemModel,
            newItem: OrderListItemModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}

sealed class ListFactoryOrderUIEvent() {
    data class OnItemClicked(val orderListItemModel: OrderListItemModel) : ListFactoryOrderUIEvent()
}
