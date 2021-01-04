package com.example.ynov_lyon_bde.ui.screens.navigationbottomview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ynov_lyon_bde.DataObject
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.RecylclerViewAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_account.*
import androidx.recyclerview.widget.RecyclerView.LayoutManager as LayoutManager1

class AccountFragment : Fragment() {

    //Fake data
    private val data = listOf(
        DataObject("Event 1", "04/01/2021"),
        DataObject("Event 2", "05/05/2021"),
        DataObject("Event 3", "15/02/2021"),
        DataObject("Event 4", "01/12/2021"),
        DataObject("Event 5", "26/05/2021"),
        DataObject("Event 6", "14/19/2021"),
        DataObject("Event 7", "22/07/2021"),
        DataObject("Event 8", "08/11/2021")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView initialized here
        recyclerView_tickets.apply {
            // Set a LinearLayoutManager to handle Android
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            // Set the custom adapter to the RecyclerView
            adapter = RecylclerViewAdapter(data)
        }
    }

    companion object {
        fun newInstance(): AccountFragment = AccountFragment()
    }
}
