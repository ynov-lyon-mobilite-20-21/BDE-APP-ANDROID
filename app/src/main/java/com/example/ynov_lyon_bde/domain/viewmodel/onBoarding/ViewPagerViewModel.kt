package com.example.ynov_lyon_bde.domain.viewmodel.onBoarding

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class ViewPagerViewModel {

     fun goToNext(viewPager: ViewPager2) {
         viewPager.currentItem++
     }

    fun goToBack(viewPager: ViewPager2) {
        viewPager.currentItem--
    }

    fun onBoardingIsFinished(activity: Activity){
        val sharedPref = activity.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}

class ViewPagerAdapter(
    list : ArrayList<Fragment>,
    fm : FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle){

    private val fragmentList = list

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}

