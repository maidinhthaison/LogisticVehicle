package com.linkon.ui.setting

import androidx.lifecycle.viewModelScope
import com.linkon.base.BaseViewModel
import com.linkon.domain.usecase.setting.GetAboutUsUseCase
import com.linkon.domain.usecase.setting.GetHelpUseCase
import com.linkon.domain.usecase.setting.GetPrivacyPolicyUseCase
import com.linkon.domain.usecase.setting.GetTermOfUseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getTermOfUseUseCase: GetTermOfUseUseCase,
    private val getPrivacyPolicyUseCase: GetPrivacyPolicyUseCase,
    private val getAboutUsUseCase: GetAboutUsUseCase,
    private val getHelpUseCase: GetHelpUseCase
) : BaseViewModel() {
    private val _uiTermOfUseModel = MutableStateFlow(TermOfUseUIState())
    val uiTermOfUseModel = _uiTermOfUseModel.asStateFlow()

    private val _uiGetPrivacyPolicyModel = MutableStateFlow(PrivacyPolicyUIState())
    val uiGetPrivacyPolicyModel = _uiGetPrivacyPolicyModel.asStateFlow()

    private val _uiAboutUsModel = MutableStateFlow(AboutUsUIState())
    val uiAboutUsModel = _uiAboutUsModel.asStateFlow()

    private val _uiHelpModel = MutableStateFlow(HelpUIState())
    val uiHelpModel = _uiHelpModel.asStateFlow()


    fun getTermOfUse()  {
        viewModelScope.launch {
            getTermOfUseUseCase(
            ).collectAsState(_uiTermOfUseModel)
        }
    }

    fun getPrivacyPolicy()  {
        viewModelScope.launch {
            getPrivacyPolicyUseCase(
            ).collectAsState(_uiGetPrivacyPolicyModel)
        }
    }

    fun getAboutUs()  {
        viewModelScope.launch {
            getAboutUsUseCase(
            ).collectAsState(_uiAboutUsModel)
        }
    }

    fun getHelpCenter()  {
        viewModelScope.launch {
            getHelpUseCase(
            ).collectAsState(_uiHelpModel)
        }
    }
}