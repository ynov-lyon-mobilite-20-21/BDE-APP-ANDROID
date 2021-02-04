package com.example.ynov_lyon_bde.ui.screens.profil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ynov_lyon_bde.data.model.Event
import com.example.ynov_lyon_bde.R

class RecyclerViewAdapter(private val data: List<Event>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    //Provides all the functionality for our list items
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var dateEvent : TextView = itemView.findViewById(R.id.date_event)
        var titleEvent : TextView = itemView.findViewById(R.id.title_event)

    }

    //Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.my_tickets, parent, false)

        return ViewHolder(view)
    }

    //Return the total count of items in the list
    override fun getItemCount(): Int {
       return data.size
    }

    //Updates list data; Associates ViewHolder data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleEvent.text = data[position].name
        holder.dateEvent.text = data[position].date
    }
}
