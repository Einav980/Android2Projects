package com.example.countries.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.countries.R
import com.squareup.picasso.Picasso

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
        textViewCountryBorders.text = intent.getStringArrayListExtra("country_borders").toString()
        Picasso.get().load(intent.getStringExtra("country_flag")).into(countryFlag)

    }
}