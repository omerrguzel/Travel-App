package com.oguzel.travel_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.oguzel.travel_app.databinding.ActivityMainBinding
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.hide
import com.oguzel.travel_app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        init()
    }

    fun init(){
        supportActionBar?.hide()
        val bottomNavigationView = binding.bottomNavigationViewMain
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.searchFragment, R.id.tripFragment, R.id.guideFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> bottomNavigationView.gone()
                R.id.searchResultFragment -> bottomNavigationView.gone()
                R.id.loginFragment ->bottomNavigationView.gone()
                R.id.registerFragment -> bottomNavigationView.gone()
                else -> bottomNavigationView.show()
            }
        }
    }
}