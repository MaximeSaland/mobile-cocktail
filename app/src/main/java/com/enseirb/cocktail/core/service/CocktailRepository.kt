package com.enseirb.cocktail.core.service

import android.util.Log
import com.enseirb.cocktail.core.model.ApiResponse
import com.enseirb.cocktail.core.model.Cocktail
import com.enseirb.cocktail.core.model.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CocktailRepository(private val apiService: ApiService) {
    private fun <T> enqueue(call: Call<ApiResponse<T>>, callback: (List<T?>) -> Unit) {
        call.enqueue(object : Callback<ApiResponse<T>> {
            override fun onResponse(call: Call<ApiResponse<T>>, response: Response<ApiResponse<T>>) {
                Log.i("OKHTTP", "Success")
                val content: List<T?> = response.body()!!.data
                callback(content)
            }

            override fun onFailure(call: Call<ApiResponse<T>>, t: Throwable) {
                Log.e("OKHTTP", "Failure: ${t.localizedMessage}")
            }
        })
    }

    fun getCocktailsByName(name: String, callback: (List<Cocktail?>) -> Unit) {
        val cocktails: Call<ApiResponse<Cocktail?>> = apiService.getCocktailsDetailsByCocktailName(name)
        enqueue(cocktails, callback)
    }

    fun getAllCategories(callback: (List<StringResponse?>) -> Unit) {
        val categories: Call<ApiResponse<StringResponse>> = apiService.getAllCategories()
        enqueue(categories, callback)
    }

    fun getAllIngredients(callback: (List<StringResponse?>) -> Unit) {
        val ingredients: Call<ApiResponse<StringResponse>> = apiService.getAllIngredidients()
        enqueue(ingredients, callback)
    }

    fun getCocktailsByCategory(category: String, callback: (List<Cocktail?>) -> Unit) {
        val cocktails: Call<ApiResponse<Cocktail?>> = apiService.getCocktailsByCategory(category)
        enqueue(cocktails, callback)
    }

    fun getCocktailsByIngredient(ingredient: String, callback: (List<Cocktail?>) -> Unit) {
        val cocktails: Call<ApiResponse<Cocktail?>> = apiService.getCocktailsDetailsByIngredientName(ingredient)
        enqueue(cocktails, callback)
    }
}