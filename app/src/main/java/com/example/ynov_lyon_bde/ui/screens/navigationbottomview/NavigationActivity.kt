package com.example.ynov_lyon_bde.ui.screens.navigationbottomview

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.domain.viewmodel.NavigationViewModel
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        supportActionBar?.hide()

        val viewModel = NavigationViewModel(supportFragmentManager)

        viewModel.makeStartingFragment()
        bottomNavigation.setOnNavigationItemSelectedListener {
            viewModel.chooseCurrentFragment(it)
            true
        }
    }
}
