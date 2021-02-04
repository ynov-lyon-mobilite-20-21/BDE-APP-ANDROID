package com.example.ynov_lyon_bde.ui.screens.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ynov_lyon_bde.R
import kotlinx.android.synthetic.main.fragment_card_description.*
import kotlinx.android.synthetic.main.fragment_card_description.view.*

class CardDescription : Fragment() {

    private val args: CardDescriptionArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_description, container, false)

        view.back_home_fragment.setOnClickListener { findNavController().popBackStack() }

        val event = args.Event

        view.eventTitle.text = event.name
        view.eventDescriptionType.text = event.type.eventType
        view.dateEvent.text = event.date
        view.eventDescription.text = event.description
        view.eventHour.text = event.date
        view.eventAddress.text = event.address
        return view
    }

}
