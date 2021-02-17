package com.example.ynov_lyon_bde.ui.screens.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.Event
import com.example.ynov_lyon_bde.data.model.EventType
import com.example.ynov_lyon_bde.data.model.ImageType
import com.example.ynov_lyon_bde.domain.viewmodel.event.EventRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_events.*

class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    private val  data = listOf(
        Event("","Espit Chupitos","22:10", EventType.STUDENTPARTY, ImageType.PARTY,"22 Rue Joseph Serlin, 69001 Lyon","Vous l'attendiez tous, la soirée la plus chaude de l'année est de retour à Espit Chupitos !\n" +
            "\n" +
            "Rendez-vous ce mercredi 13 novembre de 21h à 3h !\n" +
            "Pour l'occasion, tous les shots seront à 2€.\n" +
            "\n" +
            "L'entrée se fera uniquement sous prévente, les tarifs sont les suivants : 2€ pour les adhérents au BDE et 5€ pour les non adhérents.\n" +
            "\n" +
            " On compte sur vous pour une soirée de folie !", 0),
        Event("","Espit Night","22:10", EventType.STUDENTPARTY, ImageType.PARTY,"5 rue de chez michel","Voilà une description de l'évènement", 0),
        Event("","Espit de fou","22:10", EventType.STUDENTPARTY, ImageType.PARTY,"5 rue de chez michel","Voilà une description de l'évènement", 0),
        Event("","Espit yes","22:10", EventType.STUDENTPARTY, ImageType.PARTY,"5 rue de chez michel","Voilà une description de l'évènement", 0)
    )

    //Populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //RecyclerView initialized here
        recyclerViewCard.apply {
            //Set a LinearLayoutManager to handle Android; Correctly positions all the data in the list.
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            //Set the custom adapter to the RecyclerView; Links the RecyclerView view to a list of data.
            adapter = EventRecyclerViewAdapter(data)
        }
    }
}
