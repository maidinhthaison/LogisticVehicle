package com.linkon.ui.account

import androidx.lifecycle.viewModelScope
import com.linkon.base.BaseViewModel
import com.linkon.domain.usecase.user.GetUserInfoUseCase
import com.linkon.domain.usecase.user.LoginUseCase
import com.linkon.domain.usecase.user.LogoutUseCase
import com.linkon.domain.usecase.user.RegisterUseCase
import com.linkon.domain.usecase.user.VerifySmsCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel  @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val verifySmsCodeUseCase: VerifySmsCodeUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {
    // A private MutableStateFlow that can be updated from within the ViewModel
    private val _uiUserModel = MutableStateFlow(UserUIState())
    // A public, read-only StateFlow that the UI can collect from
    val uiUserModel = _uiUserModel.asStateFlow()

    private val _uiRegisterModel = MutableStateFlow(RegisterUIState())
    val uiRegisterModel = _uiRegisterModel.asStateFlow()

    private val _uiUserInfoModel = MutableStateFlow(UserInfoUIState())
    val uiUserInfoModel = _uiUserInfoModel.asStateFlow()

    private val _uiVerificationSmsCodeModel = MutableStateFlow(VerificationSmsCodeUiState())
    val uiVerificationSmsCodeModel = _uiVerificationSmsCodeModel.asStateFlow()

    private val _uiLogoutModel = MutableStateFlow(LogoutModelUiState())
    val uiLogoutModel = _uiLogoutModel.asStateFlow()


    fun login(phone: Long, code: Int, clientId: String)  {
        viewModelScope.launch {
            loginUseCase(
                phone = phone,
                code = code,
                clientId = clientId
            ).collectAsState(_uiUserModel)
        }
    }

    fun register(phone: String, code: Int, name: String)  {
        viewModelScope.launch {
            registerUseCase(
                name = name,
                phone = phone,
                code = code
            ).collectAsState(_uiRegisterModel)
        }
    }

    fun getUserInfo()  {
        viewModelScope.launch {
            getUserInfoUseCase(
            ).collectAsState(_uiUserInfoModel)
        }
    }

    fun verifySmsCode(phone: String, type: String)  {
        viewModelScope.launch {

            verifySmsCodeUseCase(
                phone = phone,
                type = type
            ).collectAsState(_uiVerificationSmsCodeModel)
        }
    }

    fun logout()  {
        viewModelScope.launch {
            logoutUseCase(
            ).collectAsState(_uiLogoutModel)
        }
    }
}