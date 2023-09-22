package com.example.electrocarapp.fragment

import android.content.ContentValues
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electrocarapp.R
import com.example.electrocarapp.adapter.CardCarsAdapter
import com.example.electrocarapp.data.CarsApi
import com.example.electrocarapp.data.local.CarContract
import com.example.electrocarapp.data.local.CarRepository
import com.example.electrocarapp.data.local.CarsDbHelper
import com.example.electrocarapp.model.Car
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL

class CarsFragment : Fragment() {
    private lateinit var rvCardCars: RecyclerView
    private lateinit var pbLoader: ProgressBar
    private lateinit var ivEmptyState: ImageView
    private lateinit var carsApi: CarsApi

    private val carList: List<Car> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cars, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Inits
        inits(view)
        setupRetrofit()
    }

    override fun onResume() {
        super.onResume()
        if (checkInternet(context)) {
//            callService()
            getAllCars()
        } else {
            rvCardCars.visibility = View.GONE
            pbLoader.visibility = View.GONE
            ivEmptyState.visibility = View.VISIBLE
        }
    }

    private fun inits(parent: View) {
        parent.apply {
            rvCardCars = findViewById(R.id.rv_card_cars)
            pbLoader = findViewById(R.id.pb_loader)
            ivEmptyState = findViewById(R.id.iv_empty_state)
        }
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsApi = retrofit.create(CarsApi::class.java)
    }

    private fun getAllCars() {
        carsApi.getAllCars().enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful) {
                    rvCardCars.visibility = View.VISIBLE
                    pbLoader.visibility = View.GONE
                    ivEmptyState.visibility = View.GONE
                    response.body()?.let {
                        setupRecyclerView(it)
                    }
                } else {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupRecyclerView(carList: List<Car> = this.carList) {
        val carsAdapter = CardCarsAdapter(carList)
        carsAdapter.carItemListener = { car ->
            val isSaved = CarRepository(requireContext()).saveOnDatabase(car)
        }
        rvCardCars.apply {
            adapter = carsAdapter
            layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        }

    }

    private fun checkInternet(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    /*private fun callService() {
        MyTask().execute("https://igorbag.github.io/cars-api/cars.json")
        pbLoader.visibility = View.VISIBLE
    }*/

    // HTTP Request
    /*inner class MyTask : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("My Task", "Initializing...")
        }

        override fun doInBackground(vararg p0: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {
                val urlBase = URL(p0[0])

                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                if (urlConnection.responseCode == 200) {
                    val incomingString =
                        urlConnection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(incomingString)
                } else {
                    Log.e("Error", "Service unavailable.")
                }

            } catch (ex: Exception) {
                Log.e("Error", "Error on url connection.")
                ex.printStackTrace()
            } finally {
                urlConnection?.disconnect()
            }

            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for (i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getInt("id")
                    val price = jsonArray.getJSONObject(i).getString("preco")
                    val battery = jsonArray.getJSONObject(i).getString("bateria")
                    val hp = jsonArray.getJSONObject(i).getString("potencia")
                    val recharge = jsonArray.getJSONObject(i).getString("recarga")
                    val imageUrl = jsonArray.getJSONObject(i).getString("urlPhoto")

                    val carModel = Car(id, price, battery, hp, recharge, imageUrl)

                    carList.add(carModel)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                setupRecyclerView()
                rvCardCars.visibility = View.VISIBLE
                pbLoader.visibility = View.GONE
                ivEmptyState.visibility = View.GONE
            }
        }
    }*/
}