package com.app.hapis.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.hapis.ui.screen.ComingSoonScreen
import com.app.hapis.ui.screen.MainScreen
import com.app.hapis.ui.screen.ProfileScreen

class BottomNavigationPageAdapter(fragmentManager: FragmentActivity) :
    FragmentStateAdapter(fragmentManager) {


    override fun getItemCount(): Int {
        return 3
    }


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                return  MainScreen()
            }
            1 -> ComingSoonScreen()
            else -> {

                val foodScreen = ProfileScreen()
                foodScreen
            }
        }
    }

}