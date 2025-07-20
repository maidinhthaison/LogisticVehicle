package com.linkon.ui.document

import androidx.lifecycle.viewModelScope
import com.linkon.base.BaseViewModel
import com.linkon.data.api.request.UploadShippingOrderRequestDto
import com.linkon.domain.usecase.document.UploadShippingOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(
    private val uploadShippingOrderUseCase: UploadShippingOrderUseCase
) : BaseViewModel() {

    private val _uiUploadShippingOrderModel = MutableStateFlow(UploadShippingOrderUiState())
    val uiUploadShippingOrderModel = _uiUploadShippingOrderModel.asStateFlow()

    fun uploadImage(uri: String){
        viewModelScope.launch {
            uploadShippingOrderUseCase(
                UploadShippingOrderRequestDto(uri)
            ).collectAsState(_uiUploadShippingOrderModel)
        }
    }
}