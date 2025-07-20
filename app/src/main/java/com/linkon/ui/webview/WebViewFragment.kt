package com.linkon.ui.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.linkon.R
import com.linkon.base.BaseFragment
import com.linkon.databinding.FragmentWebviewBinding
import com.linkon.ui.orders.OrdersViewModel
import com.linkon.ui.setting.SettingViewModel
import com.linkon.utils.formatHtml
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WebViewFragment :
    BaseFragment<FragmentWebviewBinding>(), OnClickListener {

    private val args: WebViewFragmentArgs by navArgs()

    private val settingViewModel by viewModels<SettingViewModel>()

    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentWebviewBinding {

        return FragmentWebviewBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        val title = args.dataToPass
        binding.toolbarOrderDetail.title =  title
        when(title){
            getString(R.string.account_term_of_use_title) -> {
                settingViewModel.getTermOfUse()
                collectTermOfUseState()
            }
            getString(R.string.account_privacy_policy_title) -> {
                settingViewModel.getPrivacyPolicy()
                collectPrivacyPolicyState()
            }
            getString(R.string.account_help_center_title) -> {
                settingViewModel.getHelpCenter()
                collectHelpCenterState()
            }
            getString(R.string.account_about_us_title) -> {
                settingViewModel.getAboutUs()
                collectAboutUsState()
            }
        }

    }

    private fun collectTermOfUseState(){
        settingViewModel.uiTermOfUseModel.collectWhenStarted { uiState ->
            val isLoading = uiState.isLoading
            binding.loadingProgress.isVisible = isLoading

            if(!isLoading){
                val termOfUserData = uiState.data
                if(termOfUserData != null){
                    Timber.d(">>>${termOfUserData.dataResponse}")
                    //binding.tvWebViewContent.text = termOfUserData.dataResponse?.let { formatHtml(it) }
                    binding.tvWebViewContent.text = termOfUserData.dataResponse
                }else{
                    Timber.d(">>>>No data")
                }
            }else{
                Timber.d(">>>>: Loading...")
            }
        }

    }
    private fun collectPrivacyPolicyState(){
        settingViewModel.uiGetPrivacyPolicyModel.collectWhenStarted { uiState ->
            val isLoading = uiState.isLoading
            binding.loadingProgress.isVisible = isLoading

            if(!isLoading){
                val privacy = uiState.data
                if(privacy != null){
                    Timber.d(">>>${privacy.dataResponse}")
                    //binding.tvWebViewContent.text = termOfUserData.dataResponse?.let { formatHtml(it) }
                    binding.tvWebViewContent.text = privacy.dataResponse
                }else{
                    Timber.d(">>>>No data")
                }
            }else{
                Timber.d(">>>>: Loading...")
            }
        }

    }

    private fun collectAboutUsState(){
        settingViewModel.uiAboutUsModel.collectWhenStarted { uiState ->
            val isLoading = uiState.isLoading
            binding.loadingProgress.isVisible = isLoading

            if(!isLoading){
                val aboutUs = uiState.data
                if(aboutUs != null){
                    Timber.d(">>>${aboutUs.dataResponse}")
                    //binding.tvWebViewContent.text = termOfUserData.dataResponse?.let { formatHtml(it) }
                    binding.tvWebViewContent.text = aboutUs.dataResponse
                }else{
                    Timber.d(">>>>No data")
                }
            }else{
                Timber.d(">>>>: Loading...")
            }
        }

    }

    private fun collectHelpCenterState(){
        settingViewModel.uiHelpModel.collectWhenStarted { uiState ->
            val isLoading = uiState.isLoading
            binding.loadingProgress.isVisible = isLoading

            if(!isLoading){
                val helpModel = uiState.data
                if(helpModel != null){
                    Timber.d(">>>${helpModel.dataResponse}")
                    val str = buildString {
                        for (item in helpModel.dataResponse!!){
                            append("Title: ${item.title}")
                            append("\n")
                            append("Content: ${item.content}")
                            append("\n\n")
                        }
                    }
                    binding.tvWebViewContent.text = str
                }else{
                    Timber.d(">>>>No data")
                }
            }else{
                Timber.d(">>>>: Loading...")
            }
        }

    }

    private fun initViews() {
        binding.apply {
            toolbarOrderDetail.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

        }
    }

    override fun onClick(p0: View?) {

    }
}