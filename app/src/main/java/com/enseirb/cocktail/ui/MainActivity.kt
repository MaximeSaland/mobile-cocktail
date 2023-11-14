package com.enseirb.cocktail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enseirb.cocktail.databinding.ActivityMainBinding
import com.enseirb.cocktail.ui.categories.CategoriesFragment
import com.enseirb.cocktail.ui.search.SearchFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private  lateinit var tabLayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        tabLayout = binding.tabLayout
        val view = binding.root
        setContentView(view)
        displayFirstFragment()
        tabLayout.addOnTabSelectedListener(this)
    }

    private fun displayFirstFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainerView.id, SearchFragment.newInstance())
            .commit()
    }

    private fun displaySecondFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainerView.id, CategoriesFragment.newInstance())
            .commit()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let {
            when(it.position) {
                0 -> displayFirstFragment()
                1 -> displaySecondFragment()
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
}