package com.example.ynov_lyon_bde.ui.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.domain.viewmodel.NavigationViewModel
import com.example.ynov_lyon_bde.ui.screens.onboarding.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val viewModel = NavigationViewModel(supportFragmentManager)


        if(!onBoardingFinished()) {
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            setContentView(R.layout.activity_main)
        }

        bottomNavigation.setOnNavigationItemSelectedListener { bottomNavigationItem ->
            viewModel.chooseCurrentFragment(bottomNavigationItem, nav_host_fragment )
            true
        }
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)
    }
}
