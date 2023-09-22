package com.example.electrocarapp.data

import com.example.electrocarapp.model.Car

object CarFactory {
    val list = listOf(
        Car(
            1,
            "R$ 300.000,00",
            "300 kWh",
            "300 hp",
            "23 min",
            "www.google.com",
            false
        ),
        Car(
            2,
            "R$ 400.000,00",
            "400 kWh",
            "400 hp",
            "20 min",
            "www.google.com",
            false
        )
    )
}