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

        var countries: ArrayList<Country> = DataSource.fetchCountries()


        mAdapter = CountryAdapter(countries)
        LinearLayoutManager(this).also { layoutManager = it }
        countries_recycler_view.layoutManager = layoutManager
        countries_recycler_view.adapter = mAdapter
    }
}


