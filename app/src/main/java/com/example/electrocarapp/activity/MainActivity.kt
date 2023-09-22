package com.example.electrocarapp.activity

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.electrocarapp.R
import com.example.electrocarapp.adapter.TabAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var tlFragments: TabLayout
    private lateinit var vpFragments: ViewPager2
    private lateinit var fabCalculate: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inits
        inits()
        setupListeners()
    }

    private fun inits() {
        this.tlFragments = findViewById(R.id.tl_fragments)

        this.vpFragments = findViewById(R.id.vp_fragments)
        this.vpFragments.adapter = TabAdapter(this)

        this.fabCalculate = findViewById(R.id.fab_calculate)
    }

    private fun setupListeners() {
        this.tlFragments.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    vpFragments.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        this.vpFragments.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tlFragments.getTabAt(position)?.select()
            }
        })

        this.fabCalculate.setOnClickListener {
            goToCalculateAutonomy()
        }
    }

    private fun goToCalculateAutonomy() {
        startActivity(Intent(this, CalculateAutonomyActivity::class.java))
    }
}