package com.enseirb.cocktail.ui.categories

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
import com.enseirb.cocktail.databinding.FragmentCategoriesBinding
import com.enseirb.cocktail.ui.adapter.CocktailAdapter
import com.enseirb.cocktail.ui.adapter.TextAdapter
import com.enseirb.cocktail.ui.fragmentInterface.IOnBackPressed
import com.enseirb.cocktail.ui.recipe.RecipeDetail


class CategoriesFragment : Fragment(), IOnBackPressed{
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var repo: CocktailRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriesAdapter: TextAdapter
    private lateinit var cocktailAdapter: CocktailAdapter

    private lateinit var selectedCategory : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        repo = CocktailRepository(ApiClient.service)

        recyclerView = binding.categoriesFragmentRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        categoriesAdapter = TextAdapter(emptyList())
        recyclerView.adapter = categoriesAdapter

        binding.progressCircular.visibility = View.VISIBLE
        repo.getAllCategories { cat: List<StringResponse?> ->
            if (cat.isEmpty()) {
                Log.i("CATEGORIES", "empty")
            }
            else {
                Log.i("CATEGORIES", "not_empty")
                categoriesAdapter.updateData(cat)
                binding.progressCircular.visibility = View.GONE
            }
        }
        selectedCategory = ""
        cocktailAdapter = CocktailAdapter(emptyList())


        categoriesAdapter.onItemClick = {
            selectedCategory = it ?: ""
            Log.i("CATEGORY", "name of the category was $it")

            binding.progressCircular.visibility = View.VISIBLE
            repo.getCocktailsByCategory (selectedCategory) { cocktails : List<Cocktail?> ->
                if (selectedCategory.isEmpty()) {
                    Log.e("CATEGORY", "name of the category was empty")
                }
                if (cocktails.isEmpty()) {
                    Log.i("CATEGORY", "no cocktail found")
                }
                else {
                    Log.i("CATEGORY", "not_empty")
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
        if (selectedCategory == "")
            return false
        selectedCategory = ""
        recyclerView.adapter = categoriesAdapter
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }




}