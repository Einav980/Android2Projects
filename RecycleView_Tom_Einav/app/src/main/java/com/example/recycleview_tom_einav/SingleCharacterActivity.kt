package com.example.recycleview_tom_einav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SingleCharacterActivity : AppCompatActivity() {

    private lateinit var textViewCharName : TextView
    private lateinit var textViewCharversion : TextView
    private lateinit var charImage : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_character)

        textViewCharName =findViewById(R.id.textViewNamePage)
        textViewCharversion = findViewById(R.id.textViewVersionPage)
        charImage = findViewById(R.id.image_view_char_page)

        textViewCharName.text = intent.getStringExtra("Character_name")
        textViewCharversion.text = intent.getStringExtra("Character_version")
        charImage.setImageResource(intent.getIntExtra("Character_image",0))

    }
}

