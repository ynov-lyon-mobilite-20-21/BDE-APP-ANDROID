package com.example.ynov_lyon_bde.domain.viewmodel.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.Event
import com.example.ynov_lyon_bde.ui.screens.event.EventsFragmentDirections


class EventRecyclerViewAdapter(private val data: List<Event>) : RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var id: TextView? = null
        var name: TextView = itemView.findViewById(R.id.eventTitle)
        var type:TextView = itemView.findViewById(R.id.eventType)
        var date: String? = null
       var description:String? = null
        var hour: String? = null
        var address:String? = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: EventRecyclerViewAdapter.ViewHolder, index: Int) {
        holder.name.text = data[index].name
        holder.type.text = data[index].type.eventType
       holder.address = data[index].address
        holder.description = data[index].description
        holder.hour = data[index].date
        holder.date = data[index].date

        holder.itemView.setOnClickListener { view ->
            val action = EventsFragmentDirections.actionEventsFragmentToCardDescription2(data[index])
            view.findNavController().navigate(action)
        }
    }
}
