package com.example.ynov_lyon_bde.domain.viewmodel

import android.text.TextUtils.replace
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.ui.screens.navigationbottomview.AccountFragment
import com.example.ynov_lyon_bde.ui.screens.navigationbottomview.EventsFragment

class NavigationViewModel(private val fragmentManager: FragmentManager) {

    private val eventFragment =  EventsFragment()
    private val accountFragment = AccountFragment()

    fun makeStartingFragment() {
        makeCurrentFragment(eventFragment)
    }

    fun chooseCurrentFragment(it: MenuItem) {
        when(it.itemId){
            R.id.eventsFragment -> makeCurrentFragment(eventFragment)
            R.id.accountFragment -> makeCurrentFragment(accountFragment)
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        fragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit ()
        }
}
