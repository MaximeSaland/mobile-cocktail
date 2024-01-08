package com.enseirb.cocktail.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.enseirb.cocktail.ui.categories.CategoriesFragment
import com.enseirb.cocktail.ui.ingredients.IngredientsFragment
import com.enseirb.cocktail.ui.search.SearchFragment

// Adapter used for handling the fragments
class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SearchFragment.newInstance()
            1 -> CategoriesFragment.newInstance()
            2 -> IngredientsFragment.newInstance()
            else -> {
                SearchFragment.newInstance()
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}