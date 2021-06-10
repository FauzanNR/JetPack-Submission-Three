package com.app.jetpacksubmissionthree

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.app.jetpacksubmissionthree.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var bottomNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bottomNavigationView = findViewById(R.id.id_bottom_naview)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        bottomNavController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigationView, bottomNavController)
        val appBarConfiguration =
            AppBarConfiguration((setOf(R.id.id_menu_movie, R.id.id_menu_tv, R.id.id_menu_fav)))
        setupActionBarWithNavController(
            navController = bottomNavController,
            configuration = appBarConfiguration
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        bottomNavController.navigateUp()
        return super.onSupportNavigateUp()
    }
}