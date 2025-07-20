package com.linkon.ui.search

import androidx.lifecycle.viewModelScope
import com.linkon.base.BaseViewModel
import com.linkon.domain.usecase.orders.GetListOrderUseCase
import com.linkon.ui.orders.OrderListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getListOrderUseCase: GetListOrderUseCase,
) : BaseViewModel() {

    private val _uiSearchOrderModel = MutableStateFlow(OrderListUiState())
    val uiSearchOrderModel = _uiSearchOrderModel.asStateFlow()


    fun searchOrders(orderNo: String? = null, page: String?= null, orderStatus: String?= null)  {

        viewModelScope.launch {
            getListOrderUseCase(
                orderNo = orderNo, pageNum = page, orderStatus = orderStatus
            ).collectAsState(_uiSearchOrderModel)
        }
    }

    fun search(queryFlow: Flow<String>) {
        queryFlow
            .debounce(500L) // Wait for 500ms of silence.
            .filter { query ->
                // Only search for non-blank text.
                query.isNotBlank() && query.length > 2
            }
            .distinctUntilChanged() // Only search if the text has actually changed.
            .onEach { query ->

                viewModelScope.launch {
                    getListOrderUseCase(
                        orderNo = query
                    ).collectAsState(_uiSearchOrderModel)
                }
            }
            .launchIn(viewModelScope) // Start collecting the flow in the ViewModel's scope.
    }

    private fun performApiSearch(orderNo: String? = null) {
        viewModelScope.launch {
            getListOrderUseCase(
                orderNo = orderNo
            ).collectAsState(_uiSearchOrderModel)
        }
    }
}