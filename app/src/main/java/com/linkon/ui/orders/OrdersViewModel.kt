package com.linkon.ui.orders

import androidx.lifecycle.viewModelScope
import com.linkon.base.BaseViewModel
import com.linkon.domain.usecase.orders.GetOrderDetailUseCase
import com.linkon.domain.usecase.orders.GetListOrderUseCase
import com.linkon.domain.usecase.orders.GetOrderTrajectoryUseCase
import com.linkon.ui.orders.detail.OrderDetailUiState
import com.linkon.ui.orders.trajectory.OrderTrajectoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getListOrderUseCase: GetListOrderUseCase,
    private val getOrderDetailUseCase: GetOrderDetailUseCase,
    private val getOrderTrajectoryUseCase: GetOrderTrajectoryUseCase
) : BaseViewModel() {

    private val _uiGetOrderListModel = MutableStateFlow(OrderListUiState())
    val uiGetOrderListModel = _uiGetOrderListModel.asStateFlow()

    private val _uiGetOrderDetailModel = MutableStateFlow(OrderDetailUiState())
    val uiGetOrderDetailModel = _uiGetOrderDetailModel.asStateFlow()

    private val _uiGetOrderTrajectoryModel = MutableStateFlow(OrderTrajectoryUiState())
    val uiGetOrderTrajectoryModel = _uiGetOrderTrajectoryModel.asStateFlow()

    fun getListOrder(orderNo: String? = null, pageNum: String?= null,
                     pageSize: String?= null,
                     orderStatus: String?= null)  {
        viewModelScope.launch {
            getListOrderUseCase(
                orderNo = orderNo, pageNum = pageNum, pageSize = pageSize, orderStatus = orderStatus
            ).collectAsState(_uiGetOrderListModel)
        }
    }

    fun getOrderDetail(orderNo: String)  {
        viewModelScope.launch {
            getOrderDetailUseCase(
                orderNo = orderNo
            ).collectAsState(_uiGetOrderDetailModel)
        }
    }

    fun getOrderTrajectory(orderNo: String)  {
        viewModelScope.launch {
            getOrderTrajectoryUseCase(
                orderNo = orderNo
            ).collectAsState(_uiGetOrderTrajectoryModel)
        }
    }

}