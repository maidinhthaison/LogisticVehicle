package com.linkon.ui.orders.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linkon.R
import com.linkon.databinding.ViewHolderOrderItemBinding
import com.linkon.domain.model.order.OrderListItemModel
import com.linkon.ui.orders.OrderStatusEnum

class PagingOrderAdapter : ListAdapter<OrderListItemModel, PagingOrderAdapter.ItemViewHolder>(
    ItemDiffCallback
) {
    var onClickedUpload: ((ListOrderUIEvent) -> Unit)? = null
    var onClickedIncident: ((ListOrderUIEvent) -> Unit)? = null
    var onClickedGetLine: ((ListOrderUIEvent) -> Unit)? = null
    var onClickedItem: ((ListOrderUIEvent) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ViewHolderOrderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ViewHolderOrderItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderListItemModel) {
            binding.tvOrderId.text = item.orderNo
            binding.tvCreatedAt.text = item.createdAt

            when (item.orderStatusName?.lowercase()) {
                OrderStatusEnum.QUEUED.value.lowercase() -> {
                    binding.apply {
                        tvOrderDetailStatus.setBackgroundResource(R.drawable.bg_warning_100_rounded_14)
                        tvOrderDetailStatus.setTextColor(binding.root.context.getColor(R.color.warning_500))

                        btnUpload.isVisible = true
                        btnGetLine.isVisible = false
                        btnIncident.isVisible = false

                        btnUpload.setOnClickListener {
                            onClickedUpload?.invoke(ListOrderUIEvent.OnItemClicked(item))
                        }
                    }
                }
                OrderStatusEnum.SHIPPED.value.lowercase() -> {
                    binding.apply {
                        tvOrderDetailStatus.setBackgroundResource(R.drawable.bg_in_progress_100_rounded_14)
                        tvOrderDetailStatus.setTextColor(binding.root.context.getColor(R.color.in_progress_500))

                        btnUpload.isVisible = true
                        btnGetLine.isVisible = true
                        btnIncident.isVisible = true

                        btnUpload.setOnClickListener {
                            onClickedUpload?.invoke(ListOrderUIEvent.OnItemClicked(item))
                        }
                        btnGetLine.setOnClickListener {
                            onClickedIncident?.invoke(ListOrderUIEvent.OnItemClicked(item))
                        }
                        btnIncident.setOnClickListener {
                            onClickedGetLine?.invoke(ListOrderUIEvent.OnItemClicked(item))
                        }
                    }
                }
                OrderStatusEnum.COMPLETED.value.lowercase() -> {
                    binding.apply {
                        tvOrderDetailStatus.setBackgroundResource(R.drawable.bg_success_100_rounded_14)
                        tvOrderDetailStatus.setTextColor(binding.root.context.getColor(R.color.success_500))

                        btnUpload.isVisible = false
                        btnGetLine.isVisible = false
                        btnIncident.isVisible = false
                    }
                }
                OrderStatusEnum.CANCELED.value.lowercase() -> {
                    binding.apply {
                        tvOrderDetailStatus.setBackgroundResource(R.drawable.bg_gray_100_rounded_14)
                        tvOrderDetailStatus.setTextColor(binding.root.context.getColor(R.color.gray_500))

                        btnUpload.isVisible = false
                        btnGetLine.isVisible = false
                        btnIncident.isVisible = false
                    }
                }
            }

            binding.tvOrderDetailStatus.text = item.orderStatusName
            binding.tvPrice.text = String.format(
                binding.root.context.getString(R.string.order_item_view_price), item.transportFee)
            binding.tvFactoryName.text = item.factoryName
            binding.tvFactoryAddress.text = item.factoryAddress
            binding.tvFactoryCheckPoint.text = item.receiverAddress
            binding.tvContainerTruck.text = item.vehicleType
            binding.tvContainerTruckCode.text = item.vehicleNo
            binding.tvTransportDateValue.text = item.transportStartTime

            binding.root.setOnClickListener {
                onClickedItem?.invoke(ListOrderUIEvent.OnItemClicked(item))
            }

        }
    }
    object ItemDiffCallback : DiffUtil.ItemCallback<OrderListItemModel>() {
        override fun areItemsTheSame(oldItem: OrderListItemModel, newItem: OrderListItemModel): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: OrderListItemModel, newItem: OrderListItemModel): Boolean {
            return oldItem == newItem
        }
    }
}
sealed class ListOrderUIEvent() {
    data class OnItemClicked(val orderListItemModel: OrderListItemModel) : ListOrderUIEvent()
}

