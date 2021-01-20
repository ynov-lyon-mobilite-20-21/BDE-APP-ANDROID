package com.example.ynov_lyon_bde.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.domain.viewmodel.NavigationViewModel
import com.example.ynov_lyon_bde.ui.screens.onboarding.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val viewModel = NavigationViewModel()

        if (!onBoardingFinished()) {
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }

        bottomNavigation.setOnNavigationItemSelectedListener { bottomNavigationItem ->
            viewModel.chooseCurrentFragment(bottomNavigationItem, base_nav_host )
            true
        }
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}
