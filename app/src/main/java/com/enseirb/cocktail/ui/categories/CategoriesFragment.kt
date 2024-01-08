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
import com.google.android.material.snackbar.Snackbar


class CategoriesFragment : Fragment(), IOnBackPressed{
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var repo: CocktailRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriesAdapter: TextAdapter
    private lateinit var cocktailAdapter: CocktailAdapter

    private lateinit var selectedCategory : String

    private fun getAllCategories() {
        binding.progressCircular.visibility = View.VISIBLE
        repo.getAllCategories { cat: List<StringResponse?>, error : String? ->
            if (error != null) {
                binding.progressCircular.visibility = View.GONE
                Snackbar.make(binding.root, "Failed retrieving data, please turn on Wi-Fi.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry") {
                        getAllCategories()
                    }
                    .show()
            }
            else {
                if (cat.isEmpty()) {
                    Log.e("CATEGORIES", "no category found")
                }
                else {
                    categoriesAdapter.updateData(cat)
                    binding.progressCircular.visibility = View.GONE
                }
            }
        }
    }

    private fun getCocktail() {
        binding.progressCircular.visibility = View.VISIBLE
        binding.noAlcohol.visibility = View.GONE
        repo.getCocktailsByCategory (selectedCategory) { cocktails : List<Cocktail?>, error : String? ->
            if (error != null) {
                binding.progressCircular.visibility = View.GONE
                Snackbar.make(binding.root, "Failed retrieving data, please turn on Wi-Fi.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry") {
                        getCocktail()
                    }
                    .show()
            }
            else {
                if (selectedCategory.isEmpty()) {
                    Log.e("CATEGORY", "name of the category was empty")
                }
                if (cocktails.isEmpty()) {
                    Log.i("CATEGORY", "no cocktail found")
                    binding.noAlcohol.visibility = View.VISIBLE
                }
                else {
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
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        repo = CocktailRepository(ApiClient.service)

        recyclerView = binding.categoriesFragmentRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        categoriesAdapter = TextAdapter(emptyList())
        recyclerView.adapter = categoriesAdapter


        getAllCategories()

        selectedCategory = ""
        cocktailAdapter = CocktailAdapter(emptyList())


        categoriesAdapter.onItemClick = {
            selectedCategory = it ?: ""
            getCocktail()
            recyclerView.adapter = cocktailAdapter
        }
        cocktailAdapter.onButtonClicked = {
            if (it == null)
                Log.e("CATEGORY", "Cocktail selected was null")
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