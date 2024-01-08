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
import com.google.android.material.snackbar.Snackbar

class IngredientsFragment : Fragment(), IOnBackPressed{
    private lateinit var binding: FragmentIngredientsBinding
    private lateinit var repo: CocktailRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var ingredientsAdapter: TextAdapter
    private lateinit var cocktailAdapter: CocktailAdapter
    private lateinit var selectedIngredient : String

    private fun getAllIngredients() {
        binding.progressCircular.visibility = View.VISIBLE
        repo.getAllIngredients { ing: List<StringResponse?>, error : String? ->
            if (error != null) {
                binding.progressCircular.visibility = View.GONE
                Snackbar.make(binding.root, "Failed retrieving data, please turn on Wi-Fi.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry") {
                        getAllIngredients()
                    }
                    .show()
            }
            else {
                if (ing.isEmpty()) {
                    Log.e("INGREDIENTS", "no ingredients found")
                } else {
                    ingredientsAdapter.updateData(ing)
                    binding.progressCircular.visibility = View.GONE
                }
            }
        }
    }

    private fun getCocktail() {
        binding.progressCircular.visibility = View.VISIBLE
        binding.noAlcohol.visibility = View.GONE
        repo.getCocktailsByIngredient(selectedIngredient) { cocktails: List<Cocktail?>, error : String? ->
            if (error != null) {
                binding.progressCircular.visibility = View.GONE
                Snackbar.make(binding.root, "Failed retrieving data, please turn on Wi-Fi.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry") {
                        getCocktail()
                    }
                    .show()
            }
            else {
                if (selectedIngredient.isEmpty()) {
                    Log.e("INGREDIENTS", "name of the ingredient was empty")
                }
                if (cocktails.isEmpty()) {
                    Log.i("INGREDIENTS", "no cocktail found")
                    binding.noAlcohol.visibility = View.VISIBLE

                } else {
                    cocktailAdapter.updateData(cocktails)
                }
                binding.progressCircular.visibility = View.GONE
            }

        }
    }
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
        getAllIngredients()
        selectedIngredient = ""
        cocktailAdapter = CocktailAdapter(emptyList())


        ingredientsAdapter.onItemClick = {
            selectedIngredient = it ?: ""
            getCocktail()
            recyclerView.adapter = cocktailAdapter
            // Notify the RecyclerView that the data set has changed
        }

        cocktailAdapter.onButtonClicked = {
            if (it == null)
                Log.e("INGREDIENTS", "Cocktail selected was null")
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