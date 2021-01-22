package com.example.ynov_lyon_bde.ui.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.domain.di.dependencyInjectionModule
import com.example.ynov_lyon_bde.domain.viewmodel.NavigationViewModel
import com.example.ynov_lyon_bde.ui.screens.onboarding.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // start Koin!
        startKoin {
            // Android context
            androidContext(this@MainActivity)
            // modules
            modules(dependencyInjectionModule)
        }

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
