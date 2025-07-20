package com.linkon.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.linkon.R
import com.linkon.base.BaseFragment
import com.linkon.databinding.FragmentHomeBinding
import com.linkon.ui.home.adapter.FactoryOrderAdapter
import com.linkon.ui.home.adapter.ListFactoryOrderUIEvent
import com.linkon.ui.home.order_detail.OrderDetailsActivity
import com.linkon.ui.orders.OrdersViewModel
import com.linkon.utils.KEY_ORDER_NO
import com.linkon.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(), OnClickListener {

    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {

        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    private val ordersViewModel by viewModels<OrdersViewModel>()
    private lateinit var factoryOrderAdapter: FactoryOrderAdapter
    private var isLoadMore = false
    private var currentPage = 0
    private val visibleThreshold = 0

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        factoryOrderAdapter = FactoryOrderAdapter()
        initViews()
        // Load the initial data
        fetchData()
        loadMoreData()

        initViews()
        initData()

    }
    private fun initData(){
        swipeRefreshLayout.setOnRefreshListener {
            showToast(requireContext(), "Refreshing data...")
            simulateDataRefresh()
        }
        factoryOrderAdapter.onClickedItem = {

            when(it){
                is ListFactoryOrderUIEvent.OnItemClicked -> {
                    /*val action = HomeFragmentDirections
                        .actionNavigationHomeToOrderDetailsFragment(
                            it.orderListItemModel.orderNo.toString()
                        )
                    findNavController().navigate(action)*/
                    val intent = Intent(requireContext(), OrderDetailsActivity::class.java)
                    intent.putExtra(KEY_ORDER_NO, it.orderListItemModel.orderNo.toString())
                    startActivity(intent)
                }

            }
        }
    }

    private fun initViews(){

        binding.apply {
            imvSearch.setOnClickListener(this@HomeFragment)
            imvNotification.setOnClickListener(this@HomeFragment)
            edtFactoryNameInputSearch.setOnClickListener(this@HomeFragment)
            // Optional: Programmatically trigger a refresh on activity start (e.g., first load)
            /*swipeRefreshLayout.post {
                //swipeRefreshLayout.isRefreshing = true
                simulateDataRefresh()
            }*/
            rvFactoryOrder.apply {
                adapter = factoryOrderAdapter
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

    override fun onResume() {
        super.onResume()
        currentPage = 0

    }
    private fun simulateDataRefresh() {
        currentPage = 0
        Timber.d(">>>>simulateDataRefresh $currentPage")
        fetchData()
    }


    override fun onClick(view: View?) {
        when (view) {
            binding.imvSearch -> {
                val action = HomeFragmentDirections.actionNavigationHomeToSearchFragment()
                findNavController().navigate(action)
            }

            binding.edtFactoryNameInputSearch -> {
                // Show select driver dialog
                val action = HomeFragmentDirections.actionNavigationHomeToSelectDriverBottomSheetDialog()
                findNavController().navigate(action)
            }

        }
    }
    private fun fetchData() {

        with(ordersViewModel) {
            getListOrder()
        }

    }

    private fun loadMoreData() {
        ordersViewModel.uiGetOrderListModel.collectWhenStarted { uiState ->
            val isLoading = uiState.isLoading
            binding.loadingProgress.isVisible = isLoading

            if(!isLoading){
                val orderModel = uiState.data

                if(orderModel != null){
                    binding.layoutEmptyOrder.root.isVisible = false
                    val listOrders = orderModel.dataResponse?.items
                    Timber.d(">>>>listOrders: ${listOrders?.size}")
                    factoryOrderAdapter.submitList(listOrders)
                    currentPage ++
                    isLoadMore = false

                    swipeRefreshLayout.isRefreshing = false
                }else{
                    Timber.d(">>>>No data")
                    binding.layoutEmptyOrder.root.isVisible = true
                }
            }else{
                Timber.d(">>>>: Loading...")
            }
        }

    }
}