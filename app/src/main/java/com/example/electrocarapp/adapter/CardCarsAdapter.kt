package com.example.electrocarapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electrocarapp.R
import com.example.electrocarapp.model.Car

class CardCarsAdapter(private val cars: List<Car>, private val isFavoriteScreen: Boolean = false): RecyclerView.Adapter<CardCarsAdapter.MyViewHolder>() {
    var carItemListener: (Car) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_card_cars, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvPrice.text = cars[position].preco
        holder.tvBattery.text = cars[position].bateria
        holder.tvHp.text = cars[position].potencia
        holder.tvRecharge.text = cars[position].recarga
        if (isFavoriteScreen)
            holder.ivFavorite.setImageResource(R.drawable.ic_filled_star)
        holder.ivFavorite.setOnClickListener {
            val car = cars[position]
            carItemListener(car)
            updateFavorite(car, holder)
        }
    }

    private fun updateFavorite(
        car: Car,
        holder: MyViewHolder
    ) {
        car.isFavorite = !car.isFavorite

        if (car.isFavorite)
            holder.ivFavorite.setImageResource(R.drawable.ic_filled_star)
        else
            holder.ivFavorite.setImageResource(R.drawable.ic_hollow_star)
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvPrice: TextView
        val tvBattery: TextView
        val tvHp: TextView
        val tvRecharge: TextView
        val ivFavorite: ImageView

        init {
            view.apply {
                tvPrice = findViewById(R.id.tv_card_price)
                tvBattery = findViewById(R.id.tv_card_battery)
                tvHp = findViewById(R.id.tv_card_hp)
                tvRecharge = findViewById(R.id.tv_card_recharge)
                ivFavorite = findViewById(R.id.iv_favorite)
            }
        }
    }
}