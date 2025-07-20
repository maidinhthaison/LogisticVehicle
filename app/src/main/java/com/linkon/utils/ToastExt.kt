package com.linkon.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.google.android.material.textview.MaterialTextView
import com.linkon.R

@SuppressLint("InflateParams")
fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT){
    val inflater = LayoutInflater.from(context)
    val layout = inflater.inflate(R.layout.layout_custom_centered_toast, null)
    val textView = layout.findViewById<MaterialTextView>(R.id.tvToastMessage)
    textView.text = message
    val toast = Toast(context)
    toast.view = layout
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.duration = duration
    toast.show()
}

@SuppressLint("InflateParams")
fun showLongToast(context: Context, message: String, duration: Int = Toast.LENGTH_LONG){
    val inflater = LayoutInflater.from(context)
    val layout = inflater.inflate(R.layout.layout_custom_centered_toast, null)
    val textView = layout.findViewById<MaterialTextView>(R.id.tvToastMessage)
    textView.text = message
    val toast = Toast(context)
    toast.view = layout
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.duration = duration
    toast.show()
}