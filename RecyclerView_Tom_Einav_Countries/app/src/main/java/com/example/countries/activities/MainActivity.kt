package com.example.countries.activities

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.adapters.CountryAdapter
import com.example.countries.data.DataSource
import com.example.countries.models.Country
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var layoutManager: RecyclerView.LayoutManager? = null;
        var mAdapter: RecyclerView.Adapter<CountryAdapter.CountryViewHolder>? = null

        val mydata  = arrayOf(arrayOf("row1", "row1", "row1")
                                ,arrayOf("row2", "row2", "row2")
                                ,arrayOf("row3", "row3", "row3"))

        var countries: ArrayList<Country> = DataSource.fetchCountries()
        main_activity_progress_bar.visibility = View.GONE

        mAdapter = CountryAdapter(countries)
        LinearLayoutManager(this).also { layoutManager = it }
        countries_recycler_view.layoutManager = layoutManager
        countries_recycler_view.adapter = mAdapter
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
    }
}


