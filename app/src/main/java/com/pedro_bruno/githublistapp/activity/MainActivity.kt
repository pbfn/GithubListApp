package com.pedro_bruno.githublistapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.pedro_bruno.githublistapp.R
import com.pedro_bruno.githublistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationController()
        setSupportActionBar(binding.toolbar)
        setToolbar()
    }

    private fun setNavigationController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.popBackStack(R.id.action_splashFragment_to_homeFragment, false)
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    hideBottomNav()
                    hideToolbar()
                }
                R.id.detailsFragment -> {
                    hideBottomNav()
                }
                else -> {
                    showToolbar()
                    showBottomNav()
                }
            }
        }
    }

    private fun setToolbar() {
        val appBarConfiguration = AppBarConfiguration(binding.bottomNavigationView.menu)
        binding.toolbar.setupWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() ||
                super.onSupportNavigateUp()
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    private fun hideToolbar() {
        binding.toolbar.visibility = View.GONE
    }

    private fun showToolbar() {
        binding.toolbar.visibility = View.VISIBLE
    }
}