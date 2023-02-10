package com.oguzel.travel_app

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oguzel.travel_app.databinding.ActivityMainBinding
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var doubleBackToExitPressedOnce = false
    private lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
    }

    private fun init() {
        supportActionBar?.hide()
        bottomNavigationView = binding.bottomNavigationViewMain
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.tripFragment,
                R.id.guideFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> bottomNavigationView.gone()
                R.id.searchResultFragment -> bottomNavigationView.gone()
                R.id.loginFragment ->bottomNavigationView.gone()
                R.id.registerFragment -> bottomNavigationView.gone()
                R.id.splashFragment -> bottomNavigationView.gone()
                else -> bottomNavigationView.show()
            }
        }
    }

    override fun onBackPressed() {
        if (bottomNavigationView.visibility == View.VISIBLE){
            if (doubleBackToExitPressedOnce) {
                finish()
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please press back again to exit", Toast.LENGTH_SHORT).show()

            Handler(Looper.myLooper()!!).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }


}