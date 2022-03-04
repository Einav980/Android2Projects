package com.example.countries.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
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
        Picasso.get().load(countries[position].flag.toString()).into(holder.countryFlag)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    inner class CountryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var countryName: TextView? = itemView.findViewById(R.id.country_card_country_name)
        var countryNativeName: TextView? = itemView.findViewById(R.id.country_card_country_native_name)
        var countryFlag: ImageView? = itemView.findViewById(R.id.country_card_country_flag_image)
        var cardView: MaterialCardView = itemView.findViewById(R.id.country_card_view)

        init {
            cardView.setOnClickListener() {
                val position: Int = adapterPosition
                Toast.makeText(it.context, "Borders: ${countries[position].borders}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
