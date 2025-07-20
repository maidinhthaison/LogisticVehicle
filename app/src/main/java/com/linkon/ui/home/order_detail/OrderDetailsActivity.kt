package com.linkon.ui.home.order_detail

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.fragment.NavHostFragment
import com.linkon.R
import com.linkon.base.BaseActivity
import com.linkon.databinding.ActivityOrderDetailBinding
import com.linkon.utils.KEY_ORDER_NO
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderDetailsActivity : BaseActivity<ActivityOrderDetailBinding>() {

    override fun initBindingObject(inflater: LayoutInflater): ActivityOrderDetailBinding {
        return ActivityOrderDetailBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orderNo = intent.getStringExtra(KEY_ORDER_NO)
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_activity_order_detail
        ) as NavHostFragment
        navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.order_detail_activity_navigation)

        navGraph.setStartDestination(R.id.orderDetailsFragment)
        val orderNoArgument = NavArgument.Builder()
            .setType(NavType.StringType)
            .setIsNullable(true)
            .setDefaultValue(orderNo)
            .build()

        navGraph.addArgument(KEY_ORDER_NO, orderNoArgument)
        navController.graph = navGraph

    }
}
