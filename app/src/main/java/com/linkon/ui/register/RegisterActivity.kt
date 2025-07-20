package com.linkon.ui.register

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
import com.linkon.databinding.ActivityRegisterBinding
import com.linkon.domain.local.UserAppSession
import com.linkon.main.MainActivity
import com.linkon.ui.account.AccountViewModel
import com.linkon.ui.login.LoginActivity
import com.linkon.utils.AppDialog
import com.linkon.utils.RETRY_GET_CODE_TIME
import com.linkon.utils.SMS_VERIFICATION_TYPE
import com.linkon.utils.showLongToast
import com.linkon.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(), OnClickListener {
    override fun initBindingObject(inflater: LayoutInflater): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }
    @Inject
    lateinit var userAppSession: UserAppSession

    private val accountViewModel by viewModels<AccountViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            termsCheckBox.text = HtmlCompat.fromHtml(
                getString(R.string.register_label_terms_link), HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvSelectCountry.text  = String.format(getString(R.string.login_select_country_code), "+86")

            phoneEditText.addTextChangedListener(TextFieldValidation(phoneEditText))
            codeEditText.addTextChangedListener(TextFieldValidation(codeEditText))

            registerButton.setOnClickListener(this@RegisterActivity)
            tvSelectCountry.setOnClickListener(this@RegisterActivity)
            tvGetCode.setOnClickListener(this@RegisterActivity)
            tvSignInLink.setOnClickListener(this@RegisterActivity)
        }
    }

    override fun onClick(view: View?) {
        when(view){
            binding.registerButton -> {

                if (isValidate()) {
                    val phone = binding.phoneEditText.text?.trim().toString()
                    val code = binding.codeEditText.text?.trim().toString()
                    val name = binding.fullNameEditText.text?.trim().toString()
                    if (code == userAppSession.getSmsCode(SMS_VERIFICATION_TYPE.REGISTER.value)) {
                        accountViewModel.register(phone = phone, code = code.toInt(),
                            name = name
                        )
                        lifecycleScope.launch {
                            accountViewModel.uiRegisterModel.collect {
                                binding.loadingProgress.isVisible = it.isLoading
                                val registerModel = it.data
                                if (registerModel?.data != null) {
                                    Timber.d(">>>>navigateToLoginScreen")
                                    showToast(this@RegisterActivity,
                                        getString(R.string.register_successfully_message))
                                    navigateToLoginInScreen()

                                }else{
                                    Timber.d(getString(R.string.register_fail_message))
                                    /*AppDialog.showErrorMessage(
                                        this@LoginActivity,
                                        getString(R.string.app_name),
                                        getString(R.string.login_fail_message),
                                        R.string.common_ok
                                    ) { _, _ ->
                                    }*/
                                }
                            }
                        }

                    } else {
                        AppDialog.showErrorMessageFromResource(
                            this@RegisterActivity, R.string.app_name,
                            R.string.register_sms_code_not_match_message,
                            R.string.common_ok
                        ) { _, _ ->
                        }
                    }
                }
            }
            binding.tvSelectCountry -> {
                showSelectCountryDialog()
            }
            binding.tvGetCode -> {
                performGetCode()
            }
            binding.tvSignInLink -> {
                navigateToLoginInScreen()

            }
        }
    }

    private fun performGetCode() {
        val phone = binding.phoneEditText.text?.trim().toString()
        // Disable button immediately to prevent multiple rapid clicks
        binding.tvGetCode.isEnabled = false
        binding.tvGetCode.setTextColor(ContextCompat.getColor(this, R.color.gray_500))

        accountViewModel.verifySmsCode(phone, SMS_VERIFICATION_TYPE.REGISTER.value)

        lifecycleScope.launch {
            accountViewModel.uiVerificationSmsCodeModel
                .filter { it.data != null && !it.isLoading } // Only proceed if data is present and not loading
                .firstOrNull() // Take the first such emission and then cancel the collection
                ?.let { uiState ->
                    val smsVerificationModel = uiState.data
                    // smsVerificationModel should not be null here due to the filter
                    val code = smsVerificationModel?.msg
                    Timber.d(">>>Code $code")
                    userAppSession.saveSmsCode(SMS_VERIFICATION_TYPE.REGISTER.value, code)
                    // Re-enable button after processing or after a delay
                    binding.tvGetCode.postDelayed({
                        binding.tvGetCode.isEnabled = true
                        binding.tvGetCode.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.primary_500))
                    }, RETRY_GET_CODE_TIME)
                    showLongToast(this@RegisterActivity, code.toString())
                } ?: run {
                // Handle case where flow completes without valid data (e.g., error occurred)
                AppDialog.showErrorMessageFromResource(
                    this@RegisterActivity, R.string.app_name,
                    R.string.message_api_error,
                    R.string.common_ok
                ) { _, _ ->
                }
                // Re-enable button
                binding.tvGetCode.isEnabled = true
                binding.tvGetCode.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.primary_500))
            }

        }
    }

    private fun showSelectCountryDialog() {
        val countries: Array<String> = resources.getStringArray(R.array.country_name)
        val code: Array<String> = resources.getStringArray(R.array.phone_code)

        AppDialog.showSelectCountryDialog(
            this@RegisterActivity,
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

    private fun navigateToLoginInScreen(){
        // Set the flags to clear the task and create a new one
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
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
        validateName() && validatePhone() && validateCode() && validateTerms()


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

                R.id.fullNameEditText -> {
                    validateName()
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
                this@RegisterActivity, R.string.app_name,
                R.string.register_fail_phone_require_message,
                R.string.common_ok
            ) { _, _ ->
            }
            false
        }else if(phone.length > 11){
            AppDialog.showErrorMessageFromResource(
                this@RegisterActivity, R.string.app_name,
                R.string.register_fail_phone_require_message,
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
                this@RegisterActivity, R.string.app_name,
                R.string.register_fail_code_require_message,
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

    /**
     * 1) field must not be empty
     */
    private fun validateName(): Boolean {

        val name = binding.fullNameEditText.text?.trim().toString()
        return if (name.isEmpty()) {
            //binding.tilEdtCode.error = getString(R.string.require_field)
            //binding.tilEdtPhone.isErrorEnabled = true
            AppDialog.showErrorMessageFromResource(
                this@RegisterActivity, R.string.app_name,
                R.string.register_fail_name_require_message,
                R.string.common_ok
            ) { _, _ ->
            }
            binding.fullNameEditText.requestFocus()
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
                this@RegisterActivity, R.string.app_name,
                R.string.register_fail_miss_check_policy,
                R.string.common_ok
            ) { _, _ ->
            }
            false
        }else {
            true
        }
    }


}