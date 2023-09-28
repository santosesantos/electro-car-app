package com.example.electrocarapp.activity

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.electrocarapp.R
import com.example.electrocarapp.adapter.TabAdapter
import com.example.electrocarapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.tabs.TabLayout
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bnvNavigation, navController)

        setupListeners()
    }

    private fun setupListeners() {
        binding.fabCalculate.setOnClickListener {
            goToCalculateAutonomy()
        }
    }

    private fun goToCalculateAutonomy() {
        startActivity(Intent(this, CalculateAutonomyActivity::class.java))
    }
}
