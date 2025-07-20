package com.linkon.utils

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.linkon.R
import com.linkon.ui.orders.detail.adapter.GridItem
import com.linkon.ui.orders.detail.adapter.GridSpacingItemDecoration
import com.linkon.ui.orders.detail.adapter.UploadDialogAdapter
import com.linkon.ui.orders.detail.adapter.UploadIncidentReportAdapter

object AppDialog {
    private fun getDialogBuilder(context: Context): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(context)
    }

    fun showErrorMessageFromResource(context: Context, title: Int, message: Int,
                                     positiveButton: Int,
                                     positiveClicked: DialogInterface.OnClickListener){
        val materialAlertDialogBuilder = getDialogBuilder(context)
        materialAlertDialogBuilder.setTitle(title)
        materialAlertDialogBuilder.setMessage(message)
        materialAlertDialogBuilder.setCancelable(false)
        materialAlertDialogBuilder.setPositiveButton(positiveButton, positiveClicked)
        materialAlertDialogBuilder.show()
    }

    fun showErrorMessage(context: Context, title: String, message: String,
                                     positiveButton: Int,
                                     positiveClicked: DialogInterface.OnClickListener){
        val materialAlertDialogBuilder = getDialogBuilder(context)
        materialAlertDialogBuilder.setTitle(title)
        materialAlertDialogBuilder.setMessage(message)
        materialAlertDialogBuilder.setCancelable(false)
        materialAlertDialogBuilder.setPositiveButton(positiveButton, positiveClicked)
        materialAlertDialogBuilder.show()
    }

    fun showConfirmDialog(
        context: Context,
        title: Int,
        message: Int,
        positiveButton: Int,
        negativeButton: Int,
        positiveClicked: DialogInterface.OnClickListener
    ) {
        val materialAlertDialogBuilder = getDialogBuilder(context)
        materialAlertDialogBuilder.setTitle(title)
        materialAlertDialogBuilder.setMessage(message)
        materialAlertDialogBuilder.setPositiveButton(positiveButton, positiveClicked)
        materialAlertDialogBuilder.setNegativeButton(negativeButton, null)
        materialAlertDialogBuilder.show()
    }
    fun showLogOutConfirmDialog(layoutInflater: LayoutInflater, layoutId: Int,
                         context: Context, isCancelable: Boolean = false,
                                logoutCallback: () -> Unit){
        val dialogView = layoutInflater.inflate(layoutId, null)
        val dialog = getDialogBuilder(context = context).create()
        dialog.apply {
            setView(dialogView)
            setCancelable(isCancelable)
        }
        // Get references to views in the custom dialog layout
        val cancelButton = dialogView.findViewById<MaterialButton>(R.id.btnCancel)
        val logoutButton = dialogView.findViewById<MaterialButton>(R.id.btnLogout)
        // Set up click listeners
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        logoutButton.setOnClickListener {
            logoutCallback()
            dialog.dismiss()
        }

        dialog.show()
    }


    fun showIncidentReportDialog(layoutInflater: LayoutInflater, layoutId: Int, context: Context,
                                 isCancelable: Boolean = false,
                                 submitCallback: () -> Unit,
                                 uploadIncidentReportAdapter: UploadIncidentReportAdapter,
                                 items: MutableList<GridItem>) {
        val dialogView = layoutInflater.inflate(layoutId, null)
        val dialog = getDialogBuilder(context = context).create()
        dialog.apply {
            setView(dialogView)
            setCancelable(isCancelable)
        }
        // Get references to views in the custom dialog layout
        val btnSubmit = dialogView.findViewById<MaterialButton>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            submitCallback()
            dialog.dismiss()
        }
        val ivCloseDialog = dialogView.findViewById<AppCompatImageView>(R.id.ivCloseDialog)
        ivCloseDialog.setOnClickListener {
            dialog.dismiss()
        }

        val spanCount = 2
        val spacingInPixels = context.resources.getDimensionPixelSize(R.dimen._20dp)
        val rvSelectIncidentImages = dialogView.findViewById<RecyclerView>(R.id.rvSelectIncidentImages)
        rvSelectIncidentImages.apply {
            layoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(GridSpacingItemDecoration(spanCount, spacingInPixels, false))
            adapter = uploadIncidentReportAdapter
        }
        uploadIncidentReportAdapter.submitList(items)

        dialog.show()
    }

    fun showUploadImagesDialog(layoutInflater: LayoutInflater, layoutId: Int, context: Context,
                               isCancelable: Boolean = false,
                               submitCallback: () -> Unit,
                               uploadDialogAdapter: UploadDialogAdapter,
                               items: MutableList<GridItem>) {
        val dialogView = layoutInflater.inflate(layoutId, null)
        val dialog = getDialogBuilder(context = context).create()
        dialog.apply {
            setView(dialogView)
            setCancelable(isCancelable)
        }
        // Get references to views in the custom dialog layout
        val btnSubmit = dialogView.findViewById<MaterialButton>(R.id.btnSubmit)
        val ivCloseDialog = dialogView.findViewById<AppCompatImageView>(R.id.ivCloseDialog)
        btnSubmit.setOnClickListener {
            submitCallback()
            dialog.dismiss()
        }
        ivCloseDialog.setOnClickListener {
            dialog.dismiss()
        }
        val spanCount = 2
        val spacingInPixels = context.resources.getDimensionPixelSize(R.dimen._20dp)
        val rvSelectImages = dialogView.findViewById<RecyclerView>(R.id.rvSelectImages)
        rvSelectImages.apply {
            layoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(GridSpacingItemDecoration(spanCount, spacingInPixels, false))
            adapter = uploadDialogAdapter
        }
        uploadDialogAdapter.submitList(items)

        dialog.show()
    }

    fun showSelectCountryDialog(context: Context,
                                        isCancelable: Boolean = false,
                                        countriesItem: Array<String>,
                                        phoneCodeItems: Array<String>,
                                        selectCallback: (position : Int,
                                        selectedCode: String) -> Unit
                                        ) {

        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, countriesItem)

        val dialogBuilder = getDialogBuilder(context = context)
        dialogBuilder.setTitle(ContextCompat.getString(context,R.string.common_choose_coutry))

        dialogBuilder.setAdapter(adapter) { _, which ->
            selectCallback(which, phoneCodeItems[which])
        }
        /*dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }*/
        val dialog = dialogBuilder.create()
        dialog.setCancelable(isCancelable)
        dialog.show()
    }
}


