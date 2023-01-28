package com.hashinology.mvvmroomretrofitmovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.hashinology.mvvmroomretrofitmovies.R
import com.hashinology.mvvmroomretrofitmovies.databinding.ActivityMainBinding
import com.hashinology.mvvmroomretrofitmovies.ui.home.MovieHomeAdapter

class MainActivity : AppCompatActivity() {
    lateinit var movieviewModel: MovieviewModel
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieviewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[MovieviewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController

        binding.bottomBar.setupWithNavController(navController)

        // 4
        // hide the bottom bar in detail fragment is a best practice
        navController.addOnDestinationChangedListener{ controller, destination, arguments ->
            when (destination.id) {
                R.id.detailsFragment -> hideBottomNav()
                else -> showBottomNav()
            }

        }
    }
    private fun hideBottomNav() {
        binding.bottomBar.visibility = View.INVISIBLE
    }

    private fun showBottomNav() {
        binding.bottomBar.visibility = View.VISIBLE
    }
}