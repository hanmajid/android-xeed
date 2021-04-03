package com.hanmajid.android.xeed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hanmajid.android.xeed.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupBinding()
        setupNavigation()
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this
    }

    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupNavigation() {
//        val rootFragments = setOf(
//            R.id.loginFragment,
//            R.id.homeFragment,
//        )
//        val navController = getNavController()
//        val appBarConfiguration = AppBarConfiguration(rootFragments)
//
//        // Setup Toolbar
//        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}