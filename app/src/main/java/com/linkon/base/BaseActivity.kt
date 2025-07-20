package com.linkon.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity <T : ViewBinding>: AppCompatActivity() {
    private var _binding : T? = null
    val binding get() = _binding!!

    abstract fun initBindingObject(inflater: LayoutInflater) : T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = initBindingObject(layoutInflater)
        val view = binding.root
        setContentView(view)

    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}