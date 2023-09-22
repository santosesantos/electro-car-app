package com.example.electrocarapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electrocarapp.R
import com.example.electrocarapp.adapter.CardCarsAdapter
import com.example.electrocarapp.data.local.CarRepository
import com.example.electrocarapp.model.Car

class FavoritesFragment: Fragment() {
    lateinit var rvFavoriteCar: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inits
        inits(view)
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
        Log.i("Tonight", "Hello there!")
    }

    fun inits(view: View) {
        this.rvFavoriteCar = view.findViewById(R.id.rv_favorite_cars)
    }

    fun setupRecyclerView() {
        val repository = CarRepository(requireContext())
        val carList = repository.getAllFavorites()

        this.rvFavoriteCar.apply {
            adapter = CardCarsAdapter(carList, true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}