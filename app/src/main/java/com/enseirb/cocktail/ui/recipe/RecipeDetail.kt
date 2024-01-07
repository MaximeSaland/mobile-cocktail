package com.enseirb.cocktail.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.enseirb.cocktail.R
import com.enseirb.cocktail.core.model.Cocktail
import com.enseirb.cocktail.core.model.Ingredient
import com.enseirb.cocktail.core.service.ApiClient
import com.enseirb.cocktail.core.service.CocktailRepository
import com.enseirb.cocktail.databinding.ActivityRecipeDetailBinding
import com.google.android.material.appbar.MaterialToolbar
import com.squareup.picasso.Picasso

class RecipeDetail : AppCompatActivity() {

    private lateinit var binding : ActivityRecipeDetailBinding
    private lateinit var repo: CocktailRepository

    private lateinit var appBar: MaterialToolbar
    private lateinit var image: ImageView
    private lateinit var category: TextView
    private lateinit var alcohol: ImageView
    private lateinit var instruction: TextView
    private lateinit var ingredients: TextView

    private fun ingredientToString(ingredient: Ingredient) : String {
        var measure = ingredient.measure
        if (measure == null)
            measure = ""
        return "- ${ingredient.name} (${measure})\n"
    }

    private fun ingredientListToString(ingredients: List<Ingredient>?) : String {
        if (ingredients == null)
            return ""

        var res = ""
        for (ing in ingredients)
            res += ingredientToString(ing)
        return res
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        repo = CocktailRepository(ApiClient.service)

        appBar = binding.appBar
        image = binding.image
        category = binding.category
        alcohol = binding.alcohol
        instruction = binding.instruction
        ingredients = binding.ingredients

        binding.progressCircular.visibility = View.VISIBLE

        var cocktailName = intent.getStringExtra("cocktailName")
        if (cocktailName == null)
            cocktailName = ""
        Log.i("SECOND_ACT", "$cocktailName")

        repo.getCocktailsByName(cocktailName) {cocktails : List<Cocktail?> ->
            if (cocktails.isEmpty()) {
                Log.i("COCKTAILS", "empty")
            } else {
                Log.i("COCKTAILS", "not_empty")
                val cocktail = cocktails[0]
                if (cocktail != null) {
                    appBar.title = cocktail.name
                    Picasso.get()
                        .load(cocktail.thumb)
                        .placeholder(R.drawable.ic_cocktail)
                        .into(image)
                    if (cocktail.alcoholic == "Alcoholic")
                        alcohol.visibility = View.GONE
                    else
                        alcohol.visibility = View.VISIBLE

                    category.text = cocktail.category
                    instruction.text = cocktail.recipe
                    ingredients.text = ingredientListToString(cocktail.ingredients)
                }
            }
            binding.progressCircular.visibility = View.GONE
        }
        setContentView(binding.root)
    }
}