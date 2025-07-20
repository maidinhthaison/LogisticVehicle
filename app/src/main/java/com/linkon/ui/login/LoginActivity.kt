package com.linkon.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.linkon.R
import com.linkon.base.BaseActivity
import com.linkon.databinding.ActivityLoginBinding
import com.linkon.domain.local.UserAppSession
import com.linkon.main.MainActivity
import com.linkon.ui.account.AccountViewModel
import com.linkon.ui.register.RegisterActivity
import com.linkon.utils.AppDialog
import com.linkon.utils.CLIENT_ID
import com.linkon.utils.RETRY_GET_CODE_TIME
import com.linkon.utils.SMS_VERIFICATION_TYPE
import com.linkon.utils.showLongToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(), OnClickListener {
    override fun initBindingObject(inflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var userAppSession: UserAppSession

    private val accountViewModel by viewModels<AccountViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setupListeners()
        initData()
    }

    private fun initViews() {
        binding.termsCheckBox.text = HtmlCompat.fromHtml(
            getString(R.string.login_label_terms_link), HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.tvSelectCountry.text =
            String.format(getString(R.string.login_select_country_code), "+86")
        //binding.phoneEditText.setText(MOCK_PHONE)
        //binding.codeEditText.setText(MOCK_CODE)
    }

    private fun initData() {
        val client = userAppSession.getClient()
        if (client != null) {
            Timber.d(">>>>navigateToHomeScreen")
            navigateToHomeScreen()
        }
    }

    private fun setupListeners() {
        binding.apply {
            phoneEditText.addTextChangedListener(TextFieldValidation(binding.phoneEditText))
            codeEditText.addTextChangedListener(TextFieldValidation(binding.codeEditText))

            loginButton.setOnClickListener(this@LoginActivity)
            tvSelectCountry.setOnClickListener(this@LoginActivity)
            tvSignUpLink.setOnClickListener(this@LoginActivity)
            tvGetCode.setOnClickListener(this@LoginActivity)

            tvSelectCountry.setOnClickListener(this@LoginActivity)

        }

    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.tvSelectCountry -> {

                val countries: Array<String> = resources.getStringArray(R.array.country_name)
                val code: Array<String> = resources.getStringArray(R.array.phone_code)

                AppDialog.showSelectCountryDialog(
                    this@LoginActivity,
                    isCancelable = true,
                    countriesItem = countries,
                    phoneCodeItems = code,
                    selectCallback = { _, selectedCode ->
                        binding.tvSelectCountry.text =
                            String.format(
                                getString(R.string.login_select_country_code),
                                selectedCode
                            )
                    }

                )
            }

            binding.tvGetCode -> {
                performGetCode()

            }

            binding.tvSignUpLink -> {
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }

            binding.loginButton -> {

                if (isValidate()) {
                    val phone = binding.phoneEditText.text?.trim().toString()
                    val code = binding.codeEditText.text?.trim().toString()
                    if (code == userAppSession.getSmsCode(SMS_VERIFICATION_TYPE.LOGIN.value)) {
                        accountViewModel.login(phone = phone.toLong(), code = code.toInt(),
                            clientId = CLIENT_ID)

                        lifecycleScope.launch {
                            accountViewModel.uiUserModel
                                .filter { !it.isLoading } // Only consider states where loading is finished
                                .first() // Take the first emission where isLoading is false
                                .let { uiState -> // 'it' here is the first non-loading UiState
                                    binding.loadingProgress.isVisible = false // Ensure progress is hidden
                                    val sessionModel = uiState.data
                                    if (sessionModel?.data != null) {
                                        Timber.d(">>>>navigateToHomeScreen")
                                        userAppSession.saveClient(sessionDataModel = sessionModel.data)
                                        accountViewModel.getUserInfo()
                                        accountViewModel.uiUserInfoModel.filter { !it.isLoading && it.data != null }.first().let { userInfo ->
                                            userAppSession.saveUserInfo(userInfoModel = userInfo.data!!)
                                            navigateToHomeScreen()
                                        }
                                    } else {
                                        Timber.d(getString(R.string.login_fail_message))
                                        AppDialog.showErrorMessage(
                                            this@LoginActivity,
                                            getString(R.string.app_name),
                                            sessionModel?.msg.toString(),
                                            R.string.common_ok
                                        ) { _, _ ->
                                        }
                                    }
                                }
                        }
                        /*lifecycleScope.launch {
                            accountViewModel.uiUserModel.collect {
                                binding.loadingProgress.isVisible = it.isLoading
                                val sessionModel = it.data
                                if (sessionModel?.data != null) {
                                    Timber.d(">>>>navigateToHomeScreen")
                                    // save tokens
                                    userAppSession.saveClient(sessionDataModel = sessionModel.data)
                                    accountViewModel.getUserInfo()
                                    // Trigger API get UserInfo
                                    lifecycleScope.launch {
                                        accountViewModel.uiUserInfoModel.collect { userInfo ->
                                            val user = userInfo.data
                                            if (user != null) {
                                                // save user
                                                userAppSession.saveUserInfo(userInfoModel = user)
                                                navigateToHomeScreen()
                                            }
                                        }
                                    }
                                }else{
                                    Timber.d(getString(R.string.login_fail_message))
                                    *//*AppDialog.showErrorMessage(
                                        this@LoginActivity,
                                        getString(R.string.app_name),
                                        getString(R.string.login_fail_message),
                                        R.string.common_ok
                                    ) { _, _ ->
                                    }*//*
                                }
                            }
                        }*/

                    } else {
                        AppDialog.showErrorMessageFromResource(
                            this@LoginActivity, R.string.app_name,
                            R.string.login_sms_code_not_match_message,
                            R.string.common_ok
                        ) { _, _ ->
                        }
                    }

                }
            }

        }
    }

    private fun performGetCode() {
        val phone = binding.phoneEditText.text?.trim().toString()
        // Disable button immediately to prevent multiple rapid clicks
        binding.tvGetCode.isEnabled = false
        binding.tvGetCode.setTextColor(ContextCompat.getColor(this, R.color.gray_500))

        accountViewModel.verifySmsCode(phone, SMS_VERIFICATION_TYPE.LOGIN.value)

        lifecycleScope.launch {
            accountViewModel.uiVerificationSmsCodeModel
                .filter { it.data != null && !it.isLoading } // Only proceed if data is present and not loading
                .firstOrNull() // Take the first such emission and then cancel the collection
                ?.let { uiState ->
                    val smsVerificationModel = uiState.data
                    // smsVerificationModel should not be null here due to the filter
                    val code = smsVerificationModel?.msg
                    Timber.d(">>>Code $code")
                    userAppSession.saveSmsCode(SMS_VERIFICATION_TYPE.LOGIN.value, code)
                    // Re-enable button after processing or after a delay
                    binding.tvGetCode.postDelayed({
                        binding.tvGetCode.isEnabled = true
                        binding.tvGetCode.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.primary_500))
                    }, RETRY_GET_CODE_TIME)
                    showLongToast(this@LoginActivity, code.toString())
                } ?: run {
                // Handle case where flow completes without valid data (e.g., error occurred)
                AppDialog.showErrorMessageFromResource(
                    this@LoginActivity, R.string.app_name,
                    R.string.message_api_error,
                    R.string.common_ok
                ) { _, _ ->
                }
                // Re-enable button
                binding.tvGetCode.isEnabled = true
                binding.tvGetCode.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.primary_500))
            }

        }
    }

    private fun navigateToHomeScreen() {
        // Set the flags to clear the task and create a new one
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    /**
     * Validation
     */
    private fun isValidate(): Boolean =
        validatePhone() && validateCode() && validateTerms()



    /**
     * applying text watcher on each text field
     */
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.phoneEditText -> {
                    validatePhone()
                }

                R.id.codeEditText -> {
                    validateCode()
                }

            }
        }
    }

    /**
     * 1) field must not be empty
     */
    private fun validatePhone(): Boolean {
        val phone = binding.phoneEditText.text?.trim().toString()
        return if (phone.isEmpty()) {
            //binding.tilEdtPhone.error = getString(R.string.require_field)
            binding.phoneEditText.requestFocus()
            //binding.tilEdtPhone.isErrorEnabled = true
            AppDialog.showErrorMessageFromResource(
                this@LoginActivity, R.string.app_name,
                R.string.login_fail_phone_require_message,
                R.string.common_ok
            ) { _, _ ->
            }
            false
        }else if(phone.length > 11){
            AppDialog.showErrorMessageFromResource(
                this@LoginActivity, R.string.app_name,
                R.string.login_fail_phone_require_message,
                R.string.common_ok
            ) { _, _ ->
            }
            binding.phoneEditText.requestFocus()
            false
        }
        else {
            //binding.tilEdtPhone.error = null
            //binding.tilEdtPhone.isErrorEnabled = false
            true
        }
    }

    /**
     * 1) field must not be empty
     */
    private fun validateCode(): Boolean {
        val code = binding.codeEditText.text?.trim().toString()
        return if (code.isEmpty()) {
            //binding.tilEdtCode.error = getString(R.string.require_field)
            //binding.tilEdtPhone.isErrorEnabled = true
            AppDialog.showErrorMessageFromResource(
                this@LoginActivity, R.string.app_name,
                R.string.login_fail_code_require_message,
                R.string.common_ok
            ) { _, _ ->
            }
            binding.codeEditText.requestFocus()
            false
        }else {
            //binding.tilEdtCode.error = null
            //binding.tilEdtCode.isErrorEnabled = false
            true
        }
    }

    private fun validateTerms(): Boolean {
        return if (!binding.termsCheckBox.isChecked) {
            AppDialog.showErrorMessageFromResource(
                this@LoginActivity, R.string.app_name,
                R.string.login_fail_miss_check_policy,
                R.string.common_ok
            ) { _, _ ->
            }
            false
        }else {
            true
        }
    }
}