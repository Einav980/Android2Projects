package com.example.recyclerview.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.activities.CharacterDetails
import com.example.recyclerview.models.Character
import com.squareup.picasso.Picasso

class CharacterAdapter(private var characters: ArrayList<Character>): RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapter.CharacterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_row_item, parent, false);
        return CharacterViewHolder(v);
    }

    override fun getItemCount(): Int {
        return characters.size;
    }

    override fun onBindViewHolder(holder: CharacterAdapter.CharacterViewHolder, position: Int) {
        holder.characterName.text = characters[position].name;
        holder.characterDescription.text = characters[position].description;
        holder.characterDubber.text = characters[position].dubber;
        Picasso.get().load(characters[position].image1).into(holder.characterImage)
    }

    inner class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var characterImage: ImageView = itemView.findViewById(R.id.character_image)
        var characterName: TextView = itemView.findViewById(R.id.character_name)
        var characterDescription: TextView = itemView.findViewById(R.id.character_description)
        var characterDubber: TextView = itemView.findViewById(R.id.character_dubber)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                var intent = Intent(it.context, CharacterDetails::class.java).apply {
                    putExtra("character_name", characters[position].name)
                    putExtra("character_description", characters[position].description)
                    putExtra("character_dubber", characters[position].dubber)
                    putExtra("character_image1", characters[position].image1)
                    putExtra("character_image2", characters[position].image2)
                }
                it.context.startActivity(intent)
            }
        }
    }
}