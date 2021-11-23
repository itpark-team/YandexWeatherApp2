package com.example.yandexweatherapp2

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class WeatherData(
    var date: LocalDate,
    var temperature: Int,
    var icon: String,
    var condition: String
) {
    companion object {
        private var conditions: Map<String, String> = mapOf(
            "clear" to "ясно",
            "partly-cloudy" to "малооблачно",
            "cloudy" to "облачно с прояснениями",
            "overcast" to "пасмурно",
            "drizzle" to "морось",
            "light-rain" to "небольшой дождь",
            "rain" to "дождь",
            "moderate-rain" to "умеренно сильный дождь",
            "heavy-rain" to "сильный дождь",
            "continuous-heavy-rain" to "длительный сильный дождь",
            "showers" to "ливень",
            "wet-snow" to "дождь со снегом",
            "light-snow" to "небольшой снег",
            "snow" to "снег",
            "snow-showers" to "снегопад",
            "hail" to "град",
            "thunderstorm" to "гроза",
            "thunderstorm-with-rain" to "дождь с грозой",
            "thunderstorm-with-hail" to "гроза с градом",
        )

        fun conditionToPrettyString(condition: String): String {
            return conditions[condition].toString()
        }

        fun dateToPrettyString(date: LocalDate):String{
            return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }

        fun getIconUrl(icon: String): String {
            return "https://yastatic.net/weather/i/icons/funky/dark/$icon.svg"
        }

        fun parse(date: String): WeatherData {
            var jsonObject = JsonParser.parseString(date).asJsonObject
            var temperature = jsonObject.getAsJsonObject("fact").get("temp").asInt
            var dateInString =
                jsonObject.getAsJsonArray("forecasts")[0].asJsonObject.get("date").asString

            var date = LocalDate.parse(dateInString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            var icon = jsonObject.getAsJsonObject("fact").get("icon").asString
            var condition = jsonObject.getAsJsonObject("fact").get("condition").asString

            return WeatherData(date, temperature, icon, condition)
        }
    }
}