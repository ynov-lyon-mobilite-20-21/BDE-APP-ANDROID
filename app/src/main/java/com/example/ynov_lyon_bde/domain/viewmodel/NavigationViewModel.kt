package com.example.ynov_lyon_bde.domain.viewmodel

import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import com.example.ynov_lyon_bde.R

class NavigationViewModel() {

    fun chooseCurrentFragment(it: MenuItem, view: View) {
        when(it.itemId){
            R.id.bottomNavigationEventButton -> view.findNavController().navigate(R.id.action_global_eventsFragment)
            R.id.bottomNavigationAccountButton -> view.findNavController().navigate(R.id.action_global_accountFragment)
        }
    }
}
