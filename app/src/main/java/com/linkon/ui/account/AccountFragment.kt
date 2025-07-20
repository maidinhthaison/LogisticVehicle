package com.linkon.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.linkon.R
import com.linkon.base.BaseFragment
import com.linkon.databinding.FragmentAccountBinding
import com.linkon.domain.AppError
import com.linkon.domain.local.UserAppSession
import com.linkon.ui.login.LoginActivity
import com.linkon.ui.register.RegisterActivity
import com.linkon.utils.AppDialog.showLogOutConfirmDialog
import com.linkon.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment :
    BaseFragment<FragmentAccountBinding>(), OnClickListener {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAccountBinding {

        return FragmentAccountBinding.inflate(inflater, container, false)
    }

    @Inject lateinit var userAppSession: UserAppSession

    private val accountViewModel by viewModels<AccountViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        binding.apply {
            switchNotification.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    // showToast(requireContext(),"Notification is enabled")
                }else{
                    // showToast(requireContext(),"Notification is disabled")
                }
            }
            constraintTermPrivacy.setOnClickListener(this@AccountFragment)
            constraintPrivacyPolicy.setOnClickListener(this@AccountFragment)
            constraintHelpCenter.setOnClickListener(this@AccountFragment)
            constraintAboutUs.setOnClickListener(this@AccountFragment)
            constraintLogout.setOnClickListener(this@AccountFragment)
        }
    }
    private fun initData() {
        val userInfo = userAppSession.getUserInfo()
        binding.apply {
            val data = userInfo?.data
            tvDriverName.text = data?.nickname
            tvPhone.text = data?.userId
            tvStatus.text = data?.loginId
        }
    }

    override fun onClick(view: View?) {
        when(view){
            binding.constraintTermPrivacy -> {
                val title = getString(R.string.account_term_of_use_title)
                navigateToWebViewFragment(title)
            }
            binding.constraintPrivacyPolicy -> {
                val title = getString(R.string.account_privacy_policy_title)
                navigateToWebViewFragment(title)
            }
            binding.constraintHelpCenter -> {
                val title = getString(R.string.account_help_center_title)
                navigateToWebViewFragment(title)
            }
            binding.constraintAboutUs -> {
                val title = getString(R.string.account_about_us_title)
                navigateToWebViewFragment(title)
            }
            binding.constraintLogout -> {
                // show dialog confirm logout
                showLogOutConfirmDialog(layoutInflater, R.layout.dialog_confirm_layout,
                    requireContext(),
                    logoutCallback = {
                        accountViewModel.logout()
                        accountViewModel.uiLogoutModel.collectWhenStarted { uiState ->
                            val isLoading = uiState.isLoading
                            binding.loadingProgress.isVisible = isLoading

                            if(!isLoading){
                                val logoutModel = uiState.data
                                if(logoutModel != null){
                                    Timber.d(">>>${logoutModel.msg}")
                                    // Clear user session
                                    userAppSession.clearSmsCode()
                                    userAppSession.clearClient()
                                    userAppSession.clearUserInfo()
                                    val intent = Intent(activity, LoginActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    activity?.finish()
                                }else{
                                    Timber.d(">>>>No data")
                                }
                            }else{
                                Timber.d(">>>>: Loading...")
                            }
                        }
                    })
            }
        }
    }


    private fun navigateToWebViewFragment(title: String) {
        val action = AccountFragmentDirections.actionNavigationAccountToWebViewFragment(title)
        findNavController().navigate(action)
    }
}
