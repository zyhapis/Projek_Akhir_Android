package com.app.hapis.ui.screen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.hapis.R
import com.app.hapis.databinding.MainScreenBinding
import com.app.hapis.ui.adapter.BottomNavigationPageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.main_screen) {
    private lateinit var binding: MainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = BottomNavigationPageAdapter(requireActivity())
        binding.viewPager2.adapter = adapter

        binding.viewPager2.isUserInputEnabled = false
        binding.bottomBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_menu -> {
                    binding.viewPager2.currentItem = 0
                    binding.bottomBar.menu.getItem(0).isChecked = true
                }
                R.id.coming_soon_menu -> {
                    binding.viewPager2.currentItem = 1
                    binding.bottomBar.menu.getItem(1).isChecked = true
                }
                R.id.profile_menu -> {
                    binding.viewPager2.currentItem = 2
                    binding.bottomBar.menu.getItem(2).isChecked = true
                }
            }
            false
        }
    }
}




