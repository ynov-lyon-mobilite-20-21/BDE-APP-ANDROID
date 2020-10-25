package com.example.ynov_lyon_bde

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNavigation  = findViewById<BottomNavigationView>(R.id.bottom_navigation_view) //Récupération de l'id bottom navigation view
        val homeFragment= HomeFragment()
        val eventsFragment = EventsFragment()
        val accountFragment = AccountFragment()

        makeCurrentFragment(homeFragment)

        //Changement de fragment aux cliques des items du menu
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> makeCurrentFragment(homeFragment)
                R.id.events -> makeCurrentFragment(eventsFragment)
                R.id.account -> makeCurrentFragment(accountFragment)
            }
            true
        }
    }

    //Fonction permettant de changer de fragment
    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.wrapper, fragment)
            commit()
        }
}
