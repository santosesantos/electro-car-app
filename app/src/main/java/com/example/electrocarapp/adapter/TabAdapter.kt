package com.example.electrocarapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.electrocarapp.fragment.CarsFragment
import com.example.electrocarapp.fragment.FavoritesFragment

class TabAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CarsFragment()
            1 -> FavoritesFragment()
            else -> CarsFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}