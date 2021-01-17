package com.example.ynov_lyon_bde.domain.viewmodel

import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.ui.screens.navigationbottomview.AccountFragment
import com.example.ynov_lyon_bde.ui.screens.navigationbottomview.EventsFragment

class NavigationViewModel(private val fragmentManager: FragmentManager) {

    fun chooseCurrentFragment(it: MenuItem, view: View) {
        when(it.itemId){
            R.id.bottomNavigationEventButton -> view.findNavController().navigate(R.id.action_global_eventsFragment2)
            R.id.bottomNavigationAccountButton -> view.findNavController().navigate(R.id.action_global_accountFragment2)
        }
    }
}
