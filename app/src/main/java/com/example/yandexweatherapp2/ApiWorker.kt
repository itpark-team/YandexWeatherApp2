package com.example.yandexweatherapp2

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ApiWorker(var applicationContext: Context) {
    private var volleyQueue = Volley.newRequestQueue(applicationContext)

    fun makeGetStringRequest(
        url: String,
        callback: (String) -> Unit,
        headers: MutableMap<String, String> = hashMapOf()
    ) {
        var request = object : StringRequest(
            Method.GET,
            url,
            Response.Listener {
                callback(it)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        volleyQueue.add(request)
    }


}
