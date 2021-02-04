package com.example.ynov_lyon_bde.ui.screens.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.domain.viewmodel.onBoarding.ViewPagerAdapter
import com.example.ynov_lyon_bde.domain.viewmodel.onBoarding.ViewPagerViewModel
import com.example.ynov_lyon_bde.ui.screens.onboarding.viewPager.FirstScreen
import com.example.ynov_lyon_bde.ui.screens.onboarding.viewPager.SecondScreen
import com.example.ynov_lyon_bde.ui.screens.onboarding.viewPager.ThirdScreen
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_view_pager.view.*
import kotlinx.android.synthetic.main.fragment_view_pager.view.next

class ViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_view_pager, container, false)
        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        view.viewPager.adapter = adapter

        val viewPager = view.viewPager
        val tabLayout = view.tab_layout
        val viewModel = ViewPagerViewModel()

        TabLayoutMediator(tabLayout,viewPager){tab, position ->
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0){
                    view.back.isVisible = false
                }else if (position > 0 ){
                    view.back.isVisible = true
                }
            }
        })

        view.next.setOnClickListener{
            if (viewPager.currentItem == 2){
                activity?.finish()
                viewModel.onBoardingIsFinished(requireActivity())
            }
            if (viewPager != null){
                viewModel.goToNext(viewPager)
            }
        }

        view.back.setOnClickListener{
            if (viewPager != null){
                viewModel.goToBack(viewPager)
            }
        }
        return view
    }
}
