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


        val mydata  = arrayOf(arrayOf("row1", "row1", "row1")
                                ,arrayOf("row2", "row2", "row2")
                                ,arrayOf("row3", "row3", "row3"))

        val myDataAdapter

        TableDataAdapter<String[]> myDataAdapter =
            new SimpleTableDataAdapter(getContext(), myData);
        TableHeaderAdapter myHeaderAdapter =
        new SimpleTableHeaderAdapter(getContext(), "Header1", "Header2", "Header3");

        TableView<String[]> table = findViewById(R.id.table);
        table.setDataAdapter(myDataAdapter);
        table.setHeaderAdapter(myHeaderAdapter);

    }
}