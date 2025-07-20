package com.linkon.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkon.R
import com.linkon.base.BaseFragment
import com.linkon.base.textChanges
import com.linkon.databinding.FragmentSearchBinding
import com.linkon.ui.home.HomeFragmentDirections
import com.linkon.ui.home.HomeViewModel
import com.linkon.ui.home.adapter.FactoryOrderAdapter
import com.linkon.ui.home.adapter.ListFactoryOrderUIEvent
import com.linkon.ui.orders.OrdersViewModel
import com.linkon.ui.search.adapter.ListSearchOrderUIEvent
import com.linkon.ui.search.adapter.SearchOrderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(), OnClickListener {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSearchBinding {

        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    private val searchViewModel by viewModels<SearchViewModel>()
    private lateinit var searchOrderAdapter: SearchOrderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchOrderAdapter = SearchOrderAdapter()
        initView()
        initData()

    }

    private fun initView() {
        binding.apply {
            tvCancel.setOnClickListener(this@SearchFragment)
            rvSearchOrder.apply {
                adapter = searchOrderAdapter
                layoutManager = LinearLayoutManager(context)
                itemAnimator = DefaultItemAnimator()
            }
        }
    }

    private fun initData() {
    // Pass the flow of text changes to the ViewModel.
        searchViewModel.search(binding.edtSearch.textChanges())
        // Observe the search results from the ViewModel.
        lifecycleScope.launch {
            searchViewModel.uiSearchOrderModel.collect { uiState ->
                val isLoading = uiState.isLoading
                binding.loadingProgress.isVisible = isLoading

                if (!isLoading) {
                    val orderModel = uiState.data

                    if (orderModel != null) {
                        binding.layoutNoRecords.root.isVisible = false
                        binding.tvSearchNumberResult.isVisible = true
                        val listOrders = orderModel.dataResponse?.items
                        Timber.d(">>>>listOrders: ${listOrders?.size}")
                        binding.tvSearchNumberResult.text = String.format(
                            getString(
                                R.string.search_label_result
                            ), listOrders?.size.toString()
                        )
                        searchOrderAdapter.submitList(listOrders)
                    } else {
                        Timber.d(">>>>No data")
                        binding.layoutNoRecords.root.isVisible = true
                        binding.tvSearchNumberResult.isVisible = false
                    }
                } else {
                    Timber.d(">>>>: Loading...")
                }
            }
        }
        searchOrderAdapter.onClickedItem = {

            when(it){
                is ListSearchOrderUIEvent.OnItemClicked -> {
                    val action = SearchFragmentDirections
                        .actionSearchFragmentToOrderDetailsFragment(
                            it.orderListItemModel.orderNo.toString()
                        )
                    findNavController().navigate(action)

                }

            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.tvCancel -> {
                findNavController().popBackStack()
            }
        }
    }

}