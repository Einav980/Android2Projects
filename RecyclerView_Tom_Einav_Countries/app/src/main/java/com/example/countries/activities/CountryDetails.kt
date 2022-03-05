package com.example.countries.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.countries.R
import com.example.countries.models.Country
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_country_details.*
import okhttp3.*
import java.io.IOException
import java.lang.StringBuilder
import java.lang.reflect.Type

class CountryDetails : AppCompatActivity() {


    private lateinit var textViewCountryName : TextView
    private lateinit var textViewCountryNativeNAme : TextView
    private lateinit var textViewCountryBorders : TextView
    private lateinit var countryFlag : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        textViewCountryName =findViewById(R.id.country_details_name)
        textViewCountryNativeNAme = findViewById(R.id.country_details_native_name_data)
        textViewCountryBorders = findViewById(R.id.country_details_border_list)
        countryFlag = findViewById(R.id.country_details_image)

        textViewCountryName.text = intent.getStringExtra("country_name")
        textViewCountryNativeNAme.text = intent.getStringExtra("country_native_name")
        var borders =  ArrayList<String>()
        intent.getStringArrayListExtra("country_borders")?.let { borders.addAll(it) }

        Picasso.get().load(intent.getStringExtra("country_flag")).into(countryFlag)
        getBordersNames(borders)
    }

    private fun getBordersNames(bordersCodes: ArrayList<String>) {
        val countryCodesBuilder = StringBuilder()
        val bordersNamesBuilder = StringBuilder()
        for(borderCode: String in bordersCodes) {
            countryCodesBuilder.append("$borderCode,")
        }

        val countryCodes = countryCodesBuilder.toString()

        val client = OkHttpClient()
        val url = "https://restcountries.com/v2/alpha?codes=$countryCodes"

        val request: Request = Request.Builder().url(url).build()

        if(countryCodes.isNotEmpty())
        {
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
                        val borders = gson.fromJson<ArrayList<Country>>(body, type)
                        for(i in 0 until borders.size){
                            bordersNamesBuilder.append("${borders[i].name} - ${borders[i].nativeName}")
                            if(i != borders.size){
                                bordersNamesBuilder.append("\n")
                            }
                        }
                        val borderNamesString = bordersNamesBuilder.toString()
                        updateBordersListText(borderNamesString)
                        stopProgressBar()
                    }
                }
            })
        }
        else{
            stopProgressBar()
            updateBordersListText("None")
        }
    }

    private fun stopProgressBar(){
        runOnUiThread{
            country_details_body.visibility = View.VISIBLE
            country_details_progress_bar.visibility = View.GONE
        }
    }

    private fun updateBordersListText(text: String){
        runOnUiThread{
            country_details_border_list.text = text
        }
    }
}