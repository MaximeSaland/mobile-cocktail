package com.enseirb.cocktail.ui.ingredients

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enseirb.cocktail.core.model.Cocktail
import com.enseirb.cocktail.core.model.StringResponse
import com.enseirb.cocktail.core.service.ApiClient
import com.enseirb.cocktail.core.service.CocktailRepository
import com.enseirb.cocktail.databinding.FragmentIngredientsBinding
import com.enseirb.cocktail.ui.adapter.CocktailAdapter
import com.enseirb.cocktail.ui.adapter.TextAdapter
import com.enseirb.cocktail.ui.fragmentInterface.IOnBackPressed
import com.enseirb.cocktail.ui.recipe.RecipeDetail

class IngredientsFragment : Fragment(), IOnBackPressed{
    private lateinit var binding: FragmentIngredientsBinding
    private lateinit var repo: CocktailRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var ingredientsAdapter: TextAdapter
    private lateinit var cocktailAdapter: CocktailAdapter
    private lateinit var selectedIngredient : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        repo = CocktailRepository(ApiClient.service)

        recyclerView = binding.ingredientsFragmentRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        ingredientsAdapter = TextAdapter(emptyList())
        recyclerView.adapter = ingredientsAdapter

        binding.progressCircular.visibility = View.VISIBLE
        repo.getAllIngredients { ing: List<StringResponse?> ->
            if (ing.isEmpty()) {
                Log.i("INGREDIENTS", "empty")
            } else {
                Log.i("INGREDIENTS", "not_empty")
                ingredientsAdapter.updateData(ing)
                binding.progressCircular.visibility = View.GONE
            }
        }
        selectedIngredient = ""
        cocktailAdapter = CocktailAdapter(emptyList())


        ingredientsAdapter.onItemClick = {
            selectedIngredient = it ?: ""
            Log.i("INGREDIENTS", "name of the category was $it")

            binding.progressCircular.visibility = View.VISIBLE
            repo.getCocktailsByIngredient(selectedIngredient) { cocktails: List<Cocktail?> ->
                if (selectedIngredient.isEmpty()) {
                    Log.e("INGREDIENTS", "name of the ingredient was empty")
                }
                if (cocktails.isEmpty()) {
                    Log.i("INGREDIENTS", "no cocktail found")
                } else {
                    Log.i("INGREDIENTS", "not_empty")
                    cocktailAdapter.updateData(cocktails)
                }
                binding.progressCircular.visibility = View.GONE
            }

            recyclerView.adapter = cocktailAdapter
            // Notify the RecyclerView that the data set has changed
        }

        cocktailAdapter.onButtonClicked = {
            if (it == null)
                Log.i("COCKTAIL SELECT", "Cocktail selected was null")
            else {
                val intent = Intent(activity, RecipeDetail::class.java)
                intent.putExtra("cocktailName", it)
                startActivity(intent)
            }
        }

        return binding.root
    }

    override fun onBackPressed(): Boolean {
        if (selectedIngredient == "")
            return false
        selectedIngredient = ""
        recyclerView.adapter = ingredientsAdapter
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance() = IngredientsFragment()
    }

}