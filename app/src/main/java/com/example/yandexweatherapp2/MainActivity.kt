package com.example.yandexweatherapp2

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.collections.HashMap
import com.caverock.androidsvg.SVG;


class MainActivity : AppCompatActivity() {

    private lateinit var buttonLoadWeather: Button
    private lateinit var textViewWeatherData: TextView
    private lateinit var imageViewWeatherIcon: ImageView

    private lateinit var apiWorker: ApiWorker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var url = "https://api.weather.yandex.ru/v2/forecast?lat=53.243562&lon=34.363425&lang=ru_RU"

        var headers = HashMap<String, String>()
        headers["X-Yandex-API-Key"] = "09363ad2-c293-4f7b-9511-0c38aa07f3d3"

        apiWorker = ApiWorker(applicationContext)

        textViewWeatherData = findViewById(R.id.textViewWeatherData)
        imageViewWeatherIcon = findViewById(R.id.imageViewWeatherIcon)

        buttonLoadWeather = findViewById(R.id.buttonLoadWeather)

        buttonLoadWeather.setOnClickListener {
            apiWorker.makeGetStringRequest(
                url, ::updateTextViewWeatherData, headers
            )
        }
    }

    private fun updateTextViewWeatherData(data: String) {

        var weatherData = WeatherData.parse(data)

        textViewWeatherData.text = "дата: ${WeatherData.dateToPrettyString(weatherData.date)}\n" +
                "температура: ${weatherData.temperature}\n" +
                "погодные условия: ${WeatherData.conditionToPrettyString(weatherData.condition)}"

        apiWorker.makeGetStringRequest(WeatherData.getIconUrl(weatherData.icon), ::updateImageViewWeatherIcon)

    }

    private fun updateImageViewWeatherIcon(data: String){

        var svg = SVG.getFromString(data)

        var h = 300F
        var w = 300F

        svg.documentWidth=w
        svg.documentHeight=h

        var image = Bitmap.createBitmap(w.toInt(), h.toInt(), Bitmap.Config.ARGB_8888)

        val canvas = Canvas(image)
        svg.renderToCanvas(canvas)

        imageViewWeatherIcon.setImageBitmap(image)
    }


}
