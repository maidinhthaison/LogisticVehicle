package com.linkon.ui.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.linkon.R
import com.linkon.base.BaseBottomSheetDialogFragment
import com.linkon.databinding.FragmentSelectDriverBottomSheetBinding
import com.linkon.ui.home.adapter.SelectDriverAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectDriverBottomSheetDialog :
    BaseBottomSheetDialogFragment<FragmentSelectDriverBottomSheetBinding>(),
OnClickListener{

    private lateinit var selectDriverAdapter : SelectDriverAdapter

    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSelectDriverBottomSheetBinding {
        return FragmentSelectDriverBottomSheetBinding.inflate(inflater, container, false)
    }

    override fun roundedCorner(): Boolean {
        return true
    }
    override fun isExpandDialog(): Boolean {
        return false
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogRounded
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.behavior.isDraggable = false
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        binding.apply {
            btnOk.setOnClickListener(this@SelectDriverBottomSheetDialog)
        }
        // Adapter
        selectDriverAdapter = SelectDriverAdapter(context = this@SelectDriverBottomSheetDialog.requireContext())
       /* binding.rvSelectDriver.apply {
            adapter = selectDriverAdapter
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }*/
       /* with(ordersViewModel) {
            ordersModel?.orderId?.let { getOrderDetail(it) }
        }*/
    }

    override fun onClick(view: View?) {
        when(view){
            binding.btnOk -> {
                dismiss()
            }
        }
    }
}