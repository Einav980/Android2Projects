package com.example.countries.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.activities.CountryDetails
import com.example.countries.models.Country
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class CountryAdapter(private var countries: ArrayList<Country>):
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_country_item, parent, false);
        return CountryViewHolder(v);
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.countryName?.text = countries[position].name
        holder.countryNativeName?.text = countries[position].nativeName
//        Picasso.get().load(countries[position].flag.toString()).into(holder.countryFlag)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    inner class CountryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var countryName: TextView? = itemView.findViewById(R.id.country_card_country_name)
        var countryNativeName: TextView? = itemView.findViewById(R.id.country_card_country_native_name)
        var countryRow: TableLayout? = itemView.findViewById(R.id.table_layout)

        init {
            countryRow?.setOnClickListener() {
                val position: Int = adapterPosition
                val intent = Intent(it.context,CountryDetails::class.java).apply {
                    putExtra("country_name", countries[position].name)
                    putExtra("country_native_name", countries[position].nativeName)
                    putExtra("country_flag", countries[position].flag.toString())
                    putStringArrayListExtra("country_borders", countries[position].borders)
                }
                it.context.startActivity(intent)
            }
        }
    }
}
