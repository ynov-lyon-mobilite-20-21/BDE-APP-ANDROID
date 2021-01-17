package com.example.ynov_lyon_bde.ui.screens.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.domain.viewmodel.ViewPagerAdapter
import com.example.ynov_lyon_bde.ui.screens.onboarding.viewPager.FirstScreen
import com.example.ynov_lyon_bde.ui.screens.onboarding.viewPager.SecondScreen
import com.example.ynov_lyon_bde.ui.screens.onboarding.viewPager.ThirdScreen
import kotlinx.android.synthetic.main.fragment_view_pager.*
import kotlinx.android.synthetic.main.fragment_view_pager.view.*

class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_view_pager)


        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter =
            ViewPagerAdapter(
                fragmentList,
                supportFragmentManager,
                lifecycle
            )

        viewPager.adapter = adapter
    }

}
