package com.example.countries.data

import com.example.countries.models.Country
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.CountDownLatch

class DataSource {
    companion object {

        fun fetchCountries(): ArrayList<Country> {

            lateinit var countries: ArrayList<Country>

            val countDownLatch = CountDownLatch(1)

            val client = OkHttpClient()
            val url = "https://restcountries.com/v2/all?fields=name,nativeName,borders,flags"

            val request: Request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    println("Failed to execute")
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        val body = response.body!!.string()
                        val gson = GsonBuilder().create()
                        val type: Type = object : TypeToken<ArrayList<Country>>() {}.type
                        countries = gson.fromJson(body, type)
                        countDownLatch.countDown()
                    }
                }
            })

            countDownLatch.await()
            return countries;
        }
    }
}