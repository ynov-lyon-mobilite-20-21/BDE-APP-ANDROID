package com.example.ynov_lyon_bde

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.my_tickets.view.*

class RecylclerViewAdapter(private val data: List<DataObject>) : RecyclerView.Adapter<RecylclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecylclerViewAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        .inflate(R.layout.my_tickets, parent, false)

        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: RecylclerViewAdapter.ViewHolder, position: Int) {
        holder.titleEvent.text = data[position].titleEvent
        holder.dateEvent.text = data[position].dateEvent
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var dateEvent : TextView
        var titleEvent : TextView

        init {
            dateEvent = itemView.findViewById(R.id.date_event)
            titleEvent = itemView.findViewById(R.id.title_event)
        }
    }

}
