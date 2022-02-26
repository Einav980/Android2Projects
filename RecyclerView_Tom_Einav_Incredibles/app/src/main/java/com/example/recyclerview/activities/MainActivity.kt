package com.example.recyclerview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.adapters.CharacterAdapter
import com.example.recyclerview.data.DataSource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null;
    private var mAdapter: RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>? = null;
    private var characters = DataSource.createDataset()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAdapter = CharacterAdapter(characters)
        layoutManager = LinearLayoutManager(this);
        characters_recycler_view.layoutManager = layoutManager
        characters_recycler_view.adapter = mAdapter;
    }
}