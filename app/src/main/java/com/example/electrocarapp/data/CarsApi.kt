package com.example.electrocarapp.data

import com.example.electrocarapp.model.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarsApi {

    @GET("cars.json")
    fun getAllCars(): Call<List<Car>>
}