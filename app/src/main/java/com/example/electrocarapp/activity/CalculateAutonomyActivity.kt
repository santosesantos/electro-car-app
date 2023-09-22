package com.example.electrocarapp.activity

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.electrocarapp.R

class CalculateAutonomyActivity: AppCompatActivity() {
    private lateinit var etKw: EditText
    private lateinit var etKm: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_autonomy)

        // Inits
        inits()
        setupListeners()

        this.tvResult.text = getSharedPref().toString()
    }

    private fun inits() {
        this.etKw = findViewById(R.id.et_kw)
        this.etKm = findViewById(R.id.et_km)
        this.btnCalculate = findViewById(R.id.btn_calculate)
        this.tvResult = findViewById(R.id.tv_result)
    }

    private fun setupListeners() {
        this.btnCalculate.setOnClickListener {
            val result = this.etKm.text.toString().toFloat() / this.etKw.text.toString().toFloat()
            tvResult.text = "$result Km/Kw"
            saveSharedPref(result)
        }
    }

    private fun saveSharedPref(value: Float) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putFloat("saved_result", value)
            apply()
        }
    }

    private fun getSharedPref(): Float {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat("saved_result", 0.0f)
    }
}