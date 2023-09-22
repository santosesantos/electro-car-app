package com.example.electrocarapp.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.electrocarapp.model.Car

class CarRepository(private val context: Context) {
    fun saveOnDatabase(car: Car): Boolean {
        val carVerification = findCarById(car.id)

        // Already exists
        if (carVerification.id != -1)
            return false

        var isSaved = false

        try {
            val db = CarsDbHelper(context).writableDatabase

            val values = ContentValues().apply {
                put(CarContract.CarEntry.COLUMN_NAME_CAR_ID, car.id)
                put(CarContract.CarEntry.COLUMN_NAME_PRECO, car.preco)
                put(CarContract.CarEntry.COLUMN_NAME_BATERIA, car.bateria)
                put(CarContract.CarEntry.COLUMN_NAME_POTENCIA, car.bateria)
                put(CarContract.CarEntry.COLUMN_NAME_RECARGA, car.recarga)
                put(CarContract.CarEntry.COLUMN_NAME_URL_PHOTO, car.urlPhoto)
            }

            Log.i("Tonight", values.toString())

            val inserted = db.insert(CarContract.CarEntry.TABLE_NAME, null, values)

            Log.i("Tonight", "Inserted rows: $inserted")
            if (inserted > 0)
                isSaved = true
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        Log.i("Tonight", isSaved.toString())
        return isSaved
    }

    fun findCarById(id: Int): Car {
        var itemCar = Car(-1, "", "", "", "", "")

        try {
            val db = CarsDbHelper(context).readableDatabase

            val columns = arrayOf(
                BaseColumns._ID,
                CarContract.CarEntry.COLUMN_NAME_CAR_ID,
                CarContract.CarEntry.COLUMN_NAME_PRECO,
                CarContract.CarEntry.COLUMN_NAME_BATERIA,
                CarContract.CarEntry.COLUMN_NAME_POTENCIA,
                CarContract.CarEntry.COLUMN_NAME_RECARGA,
                CarContract.CarEntry.COLUMN_NAME_URL_PHOTO
            )

            val filter = "${CarContract.CarEntry.COLUMN_NAME_CAR_ID} = ?"

            val filterValues = arrayOf(id.toString())

            val cursor = db.query(
                CarContract.CarEntry.TABLE_NAME,
                columns,
                filter,
                filterValues,
                null,
                null,
                null
            )

            with (cursor) {
                while (moveToNext()) {
                    itemCar = Car(
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_CAR_ID)).toInt(),
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_PRECO)),
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_BATERIA)),
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_POTENCIA)),
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_RECARGA)),
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_URL_PHOTO))
                    )
                    break
                }
            }

            cursor.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return itemCar
    }

    fun getAllFavorites(): List<Car> {
        val itemCar = mutableListOf<Car>()

        try {
            val db = CarsDbHelper(context).readableDatabase

            val columns = arrayOf(
                BaseColumns._ID,
                CarContract.CarEntry.COLUMN_NAME_CAR_ID,
                CarContract.CarEntry.COLUMN_NAME_PRECO,
                CarContract.CarEntry.COLUMN_NAME_BATERIA,
                CarContract.CarEntry.COLUMN_NAME_POTENCIA,
                CarContract.CarEntry.COLUMN_NAME_RECARGA,
                CarContract.CarEntry.COLUMN_NAME_URL_PHOTO
            )

            val cursor = db.query(
                CarContract.CarEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
            )

            with (cursor) {
                while (moveToNext()) {
                    itemCar.add(Car(
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_CAR_ID)).toInt(),
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_PRECO)),
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_BATERIA)),
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_POTENCIA)),
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_RECARGA)),
                        getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_NAME_URL_PHOTO)),
                        isFavorite = true
                    ))
                }
            }

            cursor.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        Log.i("Tonight", itemCar.toString())
        return itemCar
    }
}