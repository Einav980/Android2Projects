package com.example.recycleview_tom_einav

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView


class DataAdapter(private val dataSet: ArrayList<CharacterData>) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView
        val versionTextView: TextView
        var imageView : ImageView
        val cardView : MaterialCardView

        init {
            // Define click listener for the ViewHolder's View.
            nameTextView = view.findViewById(R.id.textViewName)
            versionTextView = view.findViewById(R.id.textViewVersion)
            imageView = view.findViewById(R.id.imageView)
            cardView = view.findViewById(R.id.card_view)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.nameTextView.text = dataSet[position].getName()
        viewHolder.versionTextView.text = dataSet[position].getVersion()
        dataSet[position].getImage()?.let { viewHolder.imageView.setImageResource(it) }

        viewHolder.cardView.setOnClickListener {
            val intent = Intent(it.context,SingleCharacterActivity::class.java).apply {
                putExtra("Character_name", dataSet[position].getName())
                putExtra("Character_version", dataSet[position].getVersion())
                putExtra("Character_image", dataSet[position].getImage())
            }
            it.context.startActivity(intent)

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

