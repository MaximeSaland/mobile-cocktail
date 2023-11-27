package com.enseirb.cocktail.core.service

import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class ApiResponse<T>(@SerializedName("drinks", alternate = ["ingredients"]) val data: List<T>)

data class StringResponse(@SerializedName("strCategory", alternate = ["strIngredient"]) val name: String)

data class Cocktail(@SerializedName("idDrink") val id: Int,
                       @SerializedName("strDrink") val name: String,
                       @SerializedName("strCategory") val category: String,
                       @SerializedName("strInstructions") val instruction: String,
                       @SerializedName("strAlcoholic") val alcoholic: String,
                       @SerializedName("strDrinkThumb") val thumbnailUrl: String,
                       @SerializedName("strIngredient1") val ingredient1: String?,
                       @SerializedName("strIngredient2") val ingredient2: String?,
                       @SerializedName("strIngredient3") val ingredient3: String?,
                       @SerializedName("strIngredient4") val ingredient4: String?,
                       @SerializedName("strIngredient5") val ingredient5: String?,
                       @SerializedName("strIngredient6") val ingredient6: String?,
                       @SerializedName("strIngredient7") val ingredient7: String?,
                       @SerializedName("strIngredient8") val ingredient8: String?,
                       @SerializedName("strIngredient9") val ingredient9: String?,
                       @SerializedName("strIngredient10") val ingredient10: String?,
                       @SerializedName("strIngredient11") val ingredient11: String?,
                       @SerializedName("strIngredient12") val ingredient12: String?,
                       @SerializedName("strIngredient13") val ingredient13: String?,
                       @SerializedName("strIngredient14") val ingredient14: String?,
                       @SerializedName("strIngredient15") val ingredient15: String?,
                       @SerializedName("strMeasure1") val measure1: String?,
                       @SerializedName("strMeasure2") val measure2: String?,
                       @SerializedName("strMeasure3") val measure3: String?,
                       @SerializedName("strMeasure4") val measure4: String?,
                       @SerializedName("strMeasure5") val measure5: String?,
                       @SerializedName("strMeasure6") val measure6: String?,
                       @SerializedName("strMeasure7") val measure7: String?,
                       @SerializedName("strMeasure8") val measure8: String?,
                       @SerializedName("strMeasure9") val measure9: String?,
                       @SerializedName("strMeasure10") val measure10: String?,
                       @SerializedName("strMeasure11") val measure11: String?,
                       @SerializedName("strMeasure12") val measure12: String?,
                       @SerializedName("strMeasure13") val measure13: String?,
                       @SerializedName("strMeasure14") val measure14: String?,
                       @SerializedName("strMeasure15") val measure15: String?
)

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