package com.enseirb.cocktail.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.enseirb.cocktail.ui.categories.CategoriesFragment
import com.enseirb.cocktail.ui.ingredients.IngredientsFragment
import com.enseirb.cocktail.ui.search.SearchFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return SearchFragment.newInstance()
            1 -> return CategoriesFragment.newInstance()
            2 -> return IngredientsFragment.newInstance()
            else -> {
                return SearchFragment.newInstance()
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}