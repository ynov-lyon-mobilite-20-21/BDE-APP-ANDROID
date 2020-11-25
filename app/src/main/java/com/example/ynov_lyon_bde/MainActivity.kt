package com.example.ynov_lyon_bde

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNavigation  = findViewById<BottomNavigationView>(R.id.bottom_navigation)//Récupération de l'id bottom navigation view
        val homeIcon = findViewById<ImageView>(R.id.home) //Récupération de l'id du logo "Home"

        //On assigne des variables aux Fragments créés
        val homeFragment= HomeFragment()
        val eventsFragment = EventsFragment()
        val accountFragment = AccountFragment()

        //La page (Fragment) par défaut
        makeCurrentFragment(homeFragment)

        //Changement de page (Fragement) aux cliques sur le logo "Home"
        homeIcon.setOnClickListener{
            makeCurrentFragment(homeFragment)
        }

        //Changement de fragment aux cliques des items du menu et de l'image (logo Home)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
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
