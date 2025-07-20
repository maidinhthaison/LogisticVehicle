package com.linkon.ui.orders

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.linkon.R
import com.linkon.base.BaseFragment
import com.linkon.databinding.FragmentOrdersBinding
import com.linkon.ui.home.order_detail.OrderDetailsActivity
import com.linkon.ui.orders.adapter.ListOrderUIEvent
import com.linkon.ui.orders.adapter.PagingOrderAdapter
import com.linkon.utils.KEY_ORDER_NO
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>(){

    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentOrdersBinding {
        return FragmentOrdersBinding.inflate(inflater, container, false)
    }

    private val ordersViewModel by viewModels<OrdersViewModel>()
    private lateinit var pagingOrderAdapter: PagingOrderAdapter
    private var isLoadMore = false
    private var currentPage = 1
    private val visibleThreshold = 0
    private var orderStatus: String = OrderStatusEnum.QUEUED.value
    private val orderNo: String = "ORD20250620001"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagingOrderAdapter = PagingOrderAdapter()
        initViews()
        // Load the initial data
        fetchData(orderNo = orderNo, pageNum =  currentPage.toString(),OrderStatusEnum.QUEUED.value)
        loadMoreData()
        pagingOrderAdapter.apply {
            onClickedUpload = {
                when (it) {
                    is ListOrderUIEvent.OnItemClicked -> {
                        Timber.d(it.orderListItemModel.orderNo)
                    }
                }
            }
            onClickedIncident = {
                when (it) {
                    is ListOrderUIEvent.OnItemClicked -> {
                        Timber.d(it.orderListItemModel.orderNo)
                    }
                }
            }
            onClickedGetLine = {
                when (it) {
                    is ListOrderUIEvent.OnItemClicked -> {
                        Timber.d(it.orderListItemModel.orderNo)
                    }
                }
            }
            onClickedItem = {
                when (it) {
                    is ListOrderUIEvent.OnItemClicked -> {
                        // navigate to detail
                        navigateToOrderDetailsFragment(it.orderListItemModel.orderNo.toString())
                    }
                }
            }
        }
    }

    private fun initViews(){
        binding.apply {
            tabLayout.apply {
                addTab((tabLayout.newTab().setText(getString(R.string.tab_label_queued))))
                addTab((tabLayout.newTab().setText(getString(R.string.tab_label_in_transit))))
                addTab((tabLayout.newTab().setText(getString(R.string.tab_label_complete))))
                addTab((tabLayout.newTab().setText(getString(R.string.tab_label_cancel))))
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        // Handle tab selected
                        when (tab.text) {
                            getString(R.string.tab_label_queued) -> {
                                currentPage = 1
                                orderStatus = OrderStatusEnum.QUEUED.value
                                fetchData(orderNo = orderNo, pageNum = currentPage.toString(),orderStatus)
                            }
                            getString(R.string.tab_label_in_transit) -> {
                                currentPage = 1
                                orderStatus = OrderStatusEnum.SHIPPED.value
                                fetchData(orderNo = orderNo, pageNum = currentPage.toString(),orderStatus)
                            }
                            getString(R.string.tab_label_complete) -> {
                                currentPage = 1
                                orderStatus = OrderStatusEnum.COMPLETED.value
                                fetchData(orderNo = orderNo, pageNum = currentPage.toString(),orderStatus)
                            }
                            getString(R.string.tab_label_cancel) -> {
                                currentPage = 1
                                orderStatus = OrderStatusEnum.CANCELED.value
                                fetchData(orderNo = orderNo, pageNum = currentPage.toString(),orderStatus)
                            }
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {
                        // Handle tab unselected
                    }

                    override fun onTabReselected(tab: TabLayout.Tab) {
                        // Handle tab reselected
                    }
                })
            }
            rvOrders.apply {
                adapter = pagingOrderAdapter
                layoutManager = LinearLayoutManager(context)
                itemAnimator = DefaultItemAnimator()
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val totalItemCount = layoutManager.itemCount
                        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                        if (!isLoadMore && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            loadMoreData()
                            isLoadMore = true
                        }
                    }
                })
            }
        }
    }

    private fun loadMoreData() {

        Timber.d(">>>currentPage: $currentPage")
        ordersViewModel.uiGetOrderListModel.collectWhenStarted { uiState ->
            val isLoading = uiState.isLoading
            binding.loadingProgress.isVisible = isLoading

            if(!isLoading){
                val orderModel = uiState.data
                if(orderModel != null){
                    val listOrders = orderModel.dataResponse?.items
                    Timber.d(">>>>listOrders: ${listOrders?.size}")
                    pagingOrderAdapter.submitList(listOrders)
                    currentPage ++
                    isLoadMore = false
                }else{
                    Timber.d(">>>>No data")
                }
            }else{
                Timber.d(">>>>: Loading...")
            }
        }

    }

    private fun fetchData(orderNo: String, pageNum: String, orderStatus: String) {

        val pageSize = "10"
        with(ordersViewModel) {
            getListOrder(orderNo = orderNo, pageNum = pageNum, pageSize = pageSize,
                orderStatus = orderStatus)
        }

    }
    private fun navigateToOrderDetailsFragment(orderNo: String) {
        /*val action = OrdersFragmentDirections.actionNavigationOrdersToOrderDetailsFragment(orderNo)
        findNavController().navigate(action)*/
        val intent = Intent(requireContext(), OrderDetailsActivity::class.java)
        intent.putExtra(KEY_ORDER_NO, orderNo)
        startActivity(intent)
    }

}