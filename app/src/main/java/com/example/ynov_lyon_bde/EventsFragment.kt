package com.example.ynov_lyon_bde

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class EventsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        val bottomNavigation = view.findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        val homeNavigation = view.findViewById<View>(R.id.home) as ImageView

        //Change fragment click home logo
        homeNavigation.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_eventsFragment_to_homeFragment)
        }

        //Change fragment click items
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.accountFragment -> Navigation.findNavController(view).navigate(R.id.action_eventsFragment_to_accountFragment)
            }
            true
        }

        return view;
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EventsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
