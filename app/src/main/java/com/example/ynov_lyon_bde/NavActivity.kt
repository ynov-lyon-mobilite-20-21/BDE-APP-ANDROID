package com.example.ynov_lyon_bde

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        val bottomNavigation = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment);
        bottomNavigation.setupWithNavController(navController);

//        val homeIcon = findViewById<ImageView>(R.id.home)
//
//        homeIcon.setOnClickListener{
////            Toast.makeText(this@MainActivity, "You clicked on ImageView.", Toast.LENGTH_SHORT).show()
//            makeCurrentFragment(HomeFragment())
//        }
    }

    //Fonction permettant de changer de fragment
//    private fun makeCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.wrapper, fragment)
//            commit()
//        }
}
