package com.enseirb.cocktail.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enseirb.cocktail.core.model.Cocktail
import com.enseirb.cocktail.core.service.ApiClient
import com.enseirb.cocktail.core.service.CocktailRepository
import com.enseirb.cocktail.databinding.FragmentSearchBinding
import com.enseirb.cocktail.ui.adapter.CocktailAdapter
import com.enseirb.cocktail.ui.backButton.IOnBackPressed
import com.enseirb.cocktail.ui.recipe.RecipeDetail

class SearchFragment : Fragment(), IOnBackPressed {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var repo: CocktailRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CocktailAdapter
    private lateinit var searchView: SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        repo = CocktailRepository(ApiClient.service)

        recyclerView = binding.searchFragmentRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CocktailAdapter(emptyList())
        recyclerView.adapter = adapter

        searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchFragmentRecyclerView.visibility = View.GONE
                binding.linearLayoutNoCocktail.visibility = View.GONE
                binding.progressCircular.visibility = View.VISIBLE
                if (query != null) {
                    repo.getCocktailsByName(query) { cocktails: List<Cocktail?>? ->
                        if (!cocktails.isNullOrEmpty()) {
                            adapter.updateData(cocktails)
                            binding.searchFragmentRecyclerView.visibility = View.VISIBLE
                        } else {
                            binding.linearLayoutNoCocktail.visibility = View.VISIBLE
                        }
                        binding.progressCircular.visibility = View.GONE
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        adapter.onButtonClicked = {
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
        return false
    }
    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}