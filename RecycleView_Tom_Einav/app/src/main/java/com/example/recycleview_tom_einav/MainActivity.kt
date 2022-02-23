package com.example.recycleview_tom_einav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dataSet : ArrayList<CharacterData>
    private lateinit var recycleView : RecyclerView
    private lateinit var layoutManager : LinearLayoutManager
    private lateinit var adapter: DataAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataSet = arrayListOf<CharacterData>()
        recycleView = findViewById(R.id.recycler_view)

        layoutManager = LinearLayoutManager(this)
        recycleView.setLayoutManager(layoutManager)

        recycleView.setItemAnimator(DefaultItemAnimator());


        var size : Int = GameOfThroneData.nameArray.size

        for( i in 0..size-1) {
            dataSet.add(CharacterData(
                    GameOfThroneData.nameArray[i],
                GameOfThroneData.versionArray[i],
                GameOfThroneData.id[i],
                GameOfThroneData.drawableArray[i]
            ))
        }

        adapter = DataAdapter(dataSet)
        recycleView.adapter = adapter

    }
}
