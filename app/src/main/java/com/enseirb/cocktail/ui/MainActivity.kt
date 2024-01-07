package com.enseirb.cocktail.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.enseirb.cocktail.R
import com.enseirb.cocktail.core.model.Cocktail
import com.enseirb.cocktail.core.service.ApiClient
import com.enseirb.cocktail.core.service.CocktailRepository
import com.enseirb.cocktail.databinding.ActivityMainBinding
import com.enseirb.cocktail.ui.adapter.FragmentAdapter
import com.enseirb.cocktail.ui.fragmentInterface.IOnBackPressed
import com.enseirb.cocktail.ui.recipe.RecipeDetail
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repo: CocktailRepository

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var randomButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2
        randomButton = binding.randomButton


        val adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        val tabs = arrayOf("Rechercher", "Catégories", "Ingrédients")
        val tabsIcons = arrayOf(R.drawable.ic_cocktail, R.drawable.ic_category, R.drawable.ic_ingredient)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabs[position]
            tab.setIcon(tabsIcons[position])
        }.attach()

        repo = CocktailRepository(ApiClient.service)
        randomButton.setOnClickListener {
            repo.getCocktailsByName("") {cocktails: List<Cocktail?> ->
                if (cocktails.isEmpty())
                    Log.e("MAIN_ACTIVITY", "No cocktail found")
                else {
                    val randomNumber = Random.nextInt(0, cocktails.size)
                    val cocktailName = cocktails[randomNumber]?.name
                    Log.i("MAIN_ACTIVITY", "Random number was $randomNumber")
                    if (cocktailName != null) {
                        val intent = Intent(this, RecipeDetail::class.java)
                        intent.putExtra("cocktailName", cocktailName)
                        startActivity(intent)
                    }
                }
            }
        }

    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val currentFragId = viewPager2.currentItem
        val currentFrag = supportFragmentManager.findFragmentByTag("f$currentFragId") as? IOnBackPressed
        if (currentFrag != null) {
            if (! currentFrag.onBackPressed())
                super.onBackPressed()
        }
    }

}