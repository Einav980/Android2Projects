package com.example.countries.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.countries.R
import com.example.countries.data.DataSource
import com.example.countries.models.Country
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CountDownLatch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mydata  = arrayOf(arrayOf("row1", "row1", "row1")
                                ,arrayOf("row2", "row2", "row2")
                                ,arrayOf("row3", "row3", "row3"))

        var countries: ArrayList<Country> = DataSource.fetchCountries()


//        val countDownLatch: CountDownLatch = CountDownLatch(1)
//
//        val client: OkHttpClient = OkHttpClient()
//        val url: String = "https://restcountries.com/v2/all"
//
//        val request: Request = Request.Builder().url(url).build()
//
//        client.newCall(request).enqueue(object : Callback {
//
//            override fun onFailure(call: Call, e: IOException) {
//                println("Failed to execute")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                response.use {
//                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
//                    val body = response.body!!.string()
//                    val gson = GsonBuilder().create()
//
//                    val countryArrayType: Type = object : TypeToken<ArrayList<Country>>() {}.type
//                    countries = Gson().fromJson(body, countryArrayType)
//                    countDownLatch.countDown()
//                }
//            }
//        })

        Picasso.get().load(countries[10].flag.toString()).into(country_image)

    }
}


