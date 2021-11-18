package com.example.yandexweatherapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private lateinit var buttonLoadWeather: Button
    private lateinit var textViewJson: TextView
    private lateinit var apiWorker: ApiWorker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var url = "https://api.weather.yandex.ru/v2/forecast?lat=53.243562&lon=34.363425&lang=ru_RU"

        var headers = HashMap<String, String>()
        headers["X-Yandex-API-Key"] = "09363ad2-c293-4f7b-9511-0c38aa07f3d3"

        apiWorker = ApiWorker(applicationContext)

        textViewJson = findViewById(R.id.textViewJson)

        buttonLoadWeather = findViewById(R.id.buttonLoadWeather)

        buttonLoadWeather.setOnClickListener {
            apiWorker.makeGetRequest(
                url, ::updateTextViewJson, headers
            )
        }
    }

    private fun updateTextViewJson(data: String) {
        textViewJson.text = data
    }


}