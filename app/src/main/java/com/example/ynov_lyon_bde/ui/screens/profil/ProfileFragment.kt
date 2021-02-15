package com.example.ynov_lyon_bde.ui.screens.profil

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ynov_lyon_bde.data.model.Event
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.EventType
import com.example.ynov_lyon_bde.data.model.ImageType
import com.example.ynov_lyon_bde.domain.services.SharedPreferencesService
import com.example.ynov_lyon_bde.ui.screens.MainActivity
import com.example.ynov_lyon_bde.ui.screens.connection.LoginActivity
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.view.*

class ProfileFragment : Fragment() {

    //Fake data
    private val data = listOf(
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        view.action_delete.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    //Populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //RecyclerView initialized here
        recyclerView_tickets.apply {
            //Set a LinearLayoutManager to handle Android; Correctly positions all the data in the list.
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            //Set the custom adapter to the RecyclerView; Links the RecyclerView view to a list of data.
            adapter = RecyclerViewAdapter(data)
        }
    }
}


