package com.linkon.ui.orders.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.linkon.R
import com.linkon.base.BaseFragment
import com.linkon.databinding.FragmentOrderDetailBinding
import com.linkon.domain.model.order.detail.OrderDetailModel
import com.linkon.ui.document.DocumentViewModel
import com.linkon.ui.orders.OrderStatusEnum
import com.linkon.ui.orders.OrdersViewModel
import com.linkon.ui.orders.detail.adapter.ActivityLogAdapter
import com.linkon.ui.orders.detail.adapter.GridItem
import com.linkon.ui.orders.detail.adapter.GridShippingReceiptAdapter
import com.linkon.ui.orders.detail.adapter.GridSpacingItemDecoration
import com.linkon.ui.orders.detail.adapter.UploadDialogAdapter
import com.linkon.ui.orders.detail.adapter.UploadIncidentReportAdapter
import com.linkon.utils.AppDialog
import com.linkon.utils.AppDialog.showIncidentReportDialog
import com.linkon.utils.AppDialog.showUploadImagesDialog
import com.linkon.utils.CLIENT_ID
import com.linkon.utils.FileUtils
import com.linkon.utils.formatPriceTo3Digits
import com.linkon.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetailsFragment :
    BaseFragment<FragmentOrderDetailBinding>(), OnClickListener {

    private lateinit var uploadDialogAdapter: UploadDialogAdapter

    private lateinit var uploadIncidentReportAdapter: UploadIncidentReportAdapter

    private lateinit var gridShippingReceiptAdapter: GridShippingReceiptAdapter

    private lateinit var activityLogAdapter: ActivityLogAdapter

    private val args: OrderDetailsFragmentArgs by navArgs()

    private val ordersViewModel by viewModels<OrdersViewModel>()

    private val documentViewModel by viewModels<DocumentViewModel>()

    @Inject
    lateinit var fileUtils: FileUtils

    private var items : MutableList<GridItem> = mutableListOf(GridItem.AddAction)
    private var itemsIncident : MutableList<GridItem> = mutableListOf(GridItem.AddAction)

    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentOrderDetailBinding {
        return FragmentOrderDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridShippingReceiptAdapter = GridShippingReceiptAdapter()
        activityLogAdapter = ActivityLogAdapter()
        initViews()
        initData()
    }

    private fun initViews() {
        binding.apply {
            toolbarOrderDetail.setNavigationOnClickListener {
                //findNavController().popBackStack()
                activity?.finish()
            }
            binding.ivArrowDown.setOnClickListener(this@OrderDetailsFragment)
            btnUpload.setOnClickListener(this@OrderDetailsFragment)
            imvIncidentWarning.setOnClickListener(this@OrderDetailsFragment)
            binding.viewHolderOrderDetailItemView.constraintLocation.setOnClickListener(this@OrderDetailsFragment)
            // Shipping receipt recycler view
            val spanCount = 3
            val spacingInPixels = root.context.resources.getDimensionPixelSize(R.dimen._18dp)
            rvShippingReceipt.apply {
                layoutManager = GridLayoutManager(root.context, spanCount)
                addItemDecoration(GridSpacingItemDecoration(spanCount, spacingInPixels, false))
                adapter = gridShippingReceiptAdapter
            }
            // Activity log recycler view
            rvActivityLog.apply {
                layoutManager = LinearLayoutManager(root.context)
                adapter = activityLogAdapter
            }

        }

    }
    private fun initData(){
        val orderNo = args.orderNo
        Timber.d(orderNo)
        with(ordersViewModel) {
            getOrderDetail(orderNo = orderNo)
        }
        ordersViewModel.uiGetOrderDetailModel.collectWhenStarted { uiState ->
            val isLoading = uiState.isLoading
            binding.loadingProgress.isVisible = isLoading

            if(!isLoading){
                val orderDetailModel = uiState.data
                if(orderDetailModel != null){
                    bindingData(orderDetailModel)

                }else{
                    Timber.d(">>>>No data")
                }
            }else{
                Timber.d(">>>>: Loading...")
            }
        }
    }
    private fun bindingData(orderDetailModel: OrderDetailModel){
        val data = orderDetailModel.data
        // Binding single item
        binding.viewHolderOrderDetailSingleItem.apply {
            if (data != null) {

                tvOrderDetailNo.text = String.format(getString(R.string.order_detail_order_no), data.orderNo)
                tvOrderDetailCreatedAt.text = String.format(getString(R.string.order_detail_created_at), data.createdAt)
                tvOrderDetailStatus.text = data.orderStatusName

                when(data.orderStatusName?.lowercase()){
                    OrderStatusEnum.QUEUED.value.lowercase() -> {
                        root.background.setTint(ContextCompat.getColor(requireContext(), R.color.warning_500))
                        tvOrderDetailStatus.setBackgroundResource(R.drawable.bg_warning_100_rounded_14)
                        tvOrderDetailStatus.setTextColor(binding.root.context.getColor(R.color.warning_500))
                    }
                    OrderStatusEnum.SHIPPED.value.lowercase() -> {
                        root.background.setTint(ContextCompat.getColor(requireContext(), R.color.in_progress_500))
                        tvOrderDetailStatus.setBackgroundResource(R.drawable.bg_in_progress_100_rounded_14)
                        tvOrderDetailStatus.setTextColor(binding.root.context.getColor(R.color.in_progress_500))
                    }

                    OrderStatusEnum.COMPLETED.value.lowercase() -> {
                        root.background.setTint(ContextCompat.getColor(requireContext(), R.color.success_500))
                        tvOrderDetailStatus.setBackgroundResource(R.drawable.bg_success_100_rounded_14)
                        tvOrderDetailStatus.setTextColor(binding.root.context.getColor(R.color.success_500))
                    }

                    OrderStatusEnum.CANCELED.value.lowercase() -> {
                        root.background.setTint(ContextCompat.getColor(requireContext(), R.color.gray_500))
                        tvOrderDetailStatus.setBackgroundResource(R.drawable.bg_gray_100_rounded_14)
                        tvOrderDetailStatus.setTextColor(binding.root.context.getColor(R.color.gray_500))
                    }

                }
            }
        }
        // Binding order item
        binding.viewHolderOrderDetailItemView.apply {
            if (data != null) {
                tvCost.text = String.format(getString(R.string.order_detail_transport_fee),
                    formatPriceTo3Digits(data.transportFee))
                /*tvCost.text = String.format(getString(R.string.order_detail_transport_fee)
                    ,formatPriceTo3Digits("999"))*/
                tvVehicle.text = data.vehicleNo
                tvGoods.text = data.goodsType
                tvFactoryName.text = data.factoryName
                tvFactoryAddress.text = data.factoryAddress
                tvFactoryCheckPoint.text = data.receiverAddress
                tvTransportDateValue.text = data.transportStartTime
                tvPaymentMethod.text = data.paymentMethod
            }

        }
        // Binding recycler view shipping receipt item
        val shippingReceiptImgDto = data?.shippingReceiptImgs
        Timber.d(">>>shippingReceiptImgDto : ${shippingReceiptImgDto?.size}")
        gridShippingReceiptAdapter.submitList(shippingReceiptImgDto)

        // Binding recycler view activity log item
        val activityLogModel = data?.activityLog
        Timber.d(">>>activityLog : ${activityLogModel?.size}")
        activityLogAdapter.submitList(activityLogModel)

    }
    override fun onClick(view: View?) {
        when (view) {
            binding.btnUpload -> {

                uploadDialogAdapter = UploadDialogAdapter(
                    plusClickedCallBack = {
                        imageChooserUpload() // This will trigger launchImagePicker
                    },
                    xClosedClickedCallBack = { itemNameClicked ->
                        val indexToRemove = items.indexOfFirst { item ->
                            item is GridItem.UploadedItem && item.imageName == itemNameClicked }
                        if (indexToRemove != -1) {
                            items.removeAt(indexToRemove)
                            // Ensure AddAction is still there if needed
                            if (items.none { it is GridItem.AddAction } && items.isNotEmpty()) { // Or some other condition
                                items.add(items.size -1 , GridItem.AddAction)
                            }
                            uploadDialogAdapter.notifyItemRemoved(indexToRemove)
                            uploadDialogAdapter.submitList(items.toList()) // Update adapter
                        }
                    }
                )

                // Crucially, submit the CURRENT list to the adapter when the dialog is shown
                uploadDialogAdapter.submitList(items.toList())

                showUploadImagesDialog(layoutInflater,
                    R.layout.dialog_upload_image_layout,
                    requireContext(),
                    submitCallback = {
                        Timber.d(">>>Submit button in dialog clicked")
                        val filesToUpload = items.filterIsInstance<GridItem.UploadedItem>()
                        lifecycleScope.launch {
                            filesToUpload.forEach { gridItem ->
                                Timber.d(">>>Uploading image path : ${gridItem.imageUriPath}")
                                documentViewModel.uploadImage(gridItem.imageUriPath)
                                documentViewModel.uiUploadShippingOrderModel
                                    .filter { !it.isLoading }
                                    .first {

                                        true
                                    }
                                    .let { uiState ->
                                        val documentModel = uiState.data
                                        if (documentModel?.data != null) {
                                            Timber.d(">>>Upload Successfully for ${gridItem.imageName}")
                                            // You could show a toast here for this specific file
                                        } else {
                                            Timber.d(">>>>Upload failed for ${gridItem.imageName}. Message: ${documentModel?.msg}")
                                            // Handle error for this specific file
                                        }
                                    }
                            }
                            Timber.d(">>>All upload attempts finished.")
                            showToast(requireContext(), "All uploads processed.")
                            binding.loadingProgress.isVisible = false // Ensure loading is hidden at the end
                        }

                    },
                    uploadDialogAdapter = uploadDialogAdapter,
                    items = items
                )

            }

            binding.imvIncidentWarning -> {
                uploadIncidentReportAdapter = UploadIncidentReportAdapter(
                    plusClickedCallBack = {
                        imageChooserUploadIncident() // This will trigger launchImagePicker
                    },
                    xClosedClickedCallBack = { itemNameClicked ->
                        val indexToRemove = itemsIncident.indexOfFirst { item ->
                            item is GridItem.UploadedItem && item.imageName == itemNameClicked }
                        if (indexToRemove != -1) {
                            itemsIncident.removeAt(indexToRemove)
                            // Ensure AddAction is still there if needed
                            if (itemsIncident.none { it is GridItem.AddAction } && itemsIncident.isNotEmpty()) { // Or some other condition
                                itemsIncident.add(itemsIncident.size -1 , GridItem.AddAction)
                            }
                            uploadIncidentReportAdapter.notifyItemRemoved(indexToRemove)
                            uploadIncidentReportAdapter.submitList(itemsIncident.toList()) // Update adapter
                        }
                    }
                )

                // Crucially, submit the CURRENT list to the adapter when the dialog is shown
                uploadIncidentReportAdapter.submitList(itemsIncident.toList())

                showIncidentReportDialog(layoutInflater,
                    R.layout.dialog_incident_report_layout,
                    requireContext(),
                    submitCallback = {
                        Timber.d(">>>Submit button in dialog clicked")
                        val filesToUpload = itemsIncident.filterIsInstance<GridItem.UploadedItem>()
                        lifecycleScope.launch {
                            filesToUpload.forEach { gridItem ->
                                Timber.d(">>>Uploading image path : ${gridItem.imageUriPath}")
                                documentViewModel.uploadImage(gridItem.imageUriPath)
                                documentViewModel.uiUploadShippingOrderModel
                                    .filter { !it.isLoading }
                                    .first {

                                        true
                                    }
                                    .let { uiState ->
                                        val documentModel = uiState.data
                                        if (documentModel?.data != null) {
                                            Timber.d(">>>Upload Successfully for ${gridItem.imageName}")
                                            // You could show a toast here for this specific file
                                        } else {
                                            Timber.d(">>>>Upload failed for ${gridItem.imageName}. Message: ${documentModel?.msg}")
                                            // Handle error for this specific file
                                        }
                                    }
                            }
                            Timber.d(">>>All upload attempts finished.")
                            showToast(requireContext(), "All uploads processed.")
                            binding.loadingProgress.isVisible = false // Ensure loading is hidden at the end
                        }

                    },
                    uploadIncidentReportAdapter = uploadIncidentReportAdapter,
                    items = itemsIncident
                )
            }

            binding.viewHolderOrderDetailItemView.constraintLocation -> {
                navigateToTrackingLiveFragment()
            }

            binding.ivArrowDown -> {
                val contentLayout = binding.rvActivityLog
                val arrowIcon = binding.ivArrowDown
                if (contentLayout.isVisible) {
                    contentLayout.visibility = View.GONE
                    arrowIcon.animate().rotation(0f).start()
                } else {
                    contentLayout.visibility = View.VISIBLE
                    arrowIcon.animate().rotation(180f).start()
                }
            }
        }
    }

    private fun navigateToTrackingLiveFragment() {
        val orderNo = args.orderNo
        val action = OrderDetailsFragmentDirections.actionOrderDetailsFragmentToTrackLiveFragment(orderNo = orderNo)
        findNavController().navigate(action)
    }


    private fun imageChooserUpload() {
        val mimeTypes = arrayOf("image/*", "application/pdf")

        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }

        launchUploadImagePicker.launch(intent)
    }

    private var launchUploadImagePicker
            : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val response = result.data
            if (response != null) {
                val selectedImageUri = response.data

                val file = fileUtils.uriToFile(requireContext(), selectedImageUri)
                val fileName: String = file?.name ?: ""
                val fileUriPath: String = file?.absolutePath ?: ""

                items.add(itemsIncident.size - 1, GridItem.UploadedItem(
                    imageUriPath = fileUriPath, imageName = fileName, imageUri = selectedImageUri!!
                ))
                // remove items if user selected duplicates
                val uniqueElements = items.toSet().toList()
                items.clear()
                items.addAll(uniqueElements)

                uploadDialogAdapter.notifyItemRangeChanged(0, items.size)
                uploadDialogAdapter.submitList(items)
            }

        }
    }

    private fun imageChooserUploadIncident() {
        val mimeTypes = arrayOf("image/*", "application/pdf")

        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }

        launchUploadIncidentPicker.launch(intent)
    }


    private var launchUploadIncidentPicker
            : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val response = result.data
            if (response != null) {
                val selectedImageUri = response.data

                val file = fileUtils.uriToFile(requireContext(), selectedImageUri)
                val fileName: String = file?.name ?: ""
                val fileUriPath: String = file?.absolutePath ?: ""

                itemsIncident.add(itemsIncident.size - 1, GridItem.UploadedItem(
                    imageUriPath = fileUriPath, imageName = fileName, imageUri = selectedImageUri!!
                ))
                // remove items if user selected duplicates
                val uniqueElements = itemsIncident.toSet().toList()
                itemsIncident.clear()
                itemsIncident.addAll(uniqueElements)

                uploadIncidentReportAdapter.notifyItemRangeChanged(0, itemsIncident.size)
                uploadIncidentReportAdapter.submitList(itemsIncident)
            }

        }
    }


}