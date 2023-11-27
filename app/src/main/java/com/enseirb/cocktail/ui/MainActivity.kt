package com.enseirb.cocktail.ui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.enseirb.cocktail.R
import com.enseirb.cocktail.core.service.ApiClient
import com.enseirb.cocktail.core.service.CocktailRepository
import com.enseirb.cocktail.databinding.ActivityMainBinding
import com.enseirb.cocktail.ui.adapter.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2

        //tabLayout.addOnTabSelectedListener(this)
        val adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        val tabs = arrayOf("Rechercher", "Catégories", "Ingrédients")
        val tabsIcons = arrayOf(R.drawable.ic_cocktail, R.drawable.ic_category, R.drawable.ic_ingredient)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabs[position]
            tab.setIcon(tabsIcons[position])
        }.attach()

        val repo = CocktailRepository(ApiClient.service)

        repo.getAllCategories {c ->
            c.forEach {
                Log.i("CATEG", it.toString())
            }
        }

        repo.getAllIngredients { i ->
            i.forEach {
                Log.i("ING", it.toString())
            }
        }

        repo.getCocktailsByName("margarita") { c ->
            c.forEach {
                Log.i("COCK-NAME", it.toString())
            }
        }

        repo.getCocktailsByCategory("Ordinary_Drink") { c ->
            c.forEach {
                Log.i("COCK-CATEG", it.toString())
            }
        }

        repo.getCocktailsByIngredient("vodka") {
            it.forEach {
                Log.i("COCK-ING", it.toString())
            }
        }
    }
}