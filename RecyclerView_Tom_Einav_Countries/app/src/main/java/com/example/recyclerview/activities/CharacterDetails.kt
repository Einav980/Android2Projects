package com.example.recyclerview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerview.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character_details.*

class CharacterDetails : AppCompatActivity() {
    private var characterName = null
    private var characterDescription = null
    private var characterDubber = null
    private var characterImage1 = null
    private var characterImage2 = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        character_details_character_name.text = intent.extras?.getString("character_name")
        character_details_character_description.text = intent.extras?.getString("character_description")
        character_details_character_dubber.text = intent.extras?.getString("character_dubber")
        Picasso.get().load(intent.extras?.getString("character_image1")).into(character_details_image1)
        Picasso.get().load(intent.extras?.getString("character_image2")).into(character_details_image2)
    }
}