package com.enseirb.cocktail.core.service

import com.enseirb.cocktail.core.model.ApiResponse
import com.enseirb.cocktail.core.model.Cocktail
import com.enseirb.cocktail.core.model.StringResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php")
    fun getCocktailsDetailsByCocktailName(@Query("s") name: String): Call<ApiResponse<Cocktail?>>
    @GET("filter.php")
    fun getCocktailsDetailsByIngredientName(@Query("i") name: String): Call<ApiResponse<Cocktail?>>
    @GET("list.php?c=list")
    fun getAllCategories(): Call<ApiResponse<StringResponse>>
    @GET("list.php?c=list")
    fun getAllIngredidients(): Call<ApiResponse<StringResponse>>
    @GET("list.php?a=list")
    fun getAllAlcoholic(): Call<ApiResponse<String>>
    @GET("filter.php")
    fun getCocktailsByCategory(@Query("c") category: String): Call<ApiResponse<Cocktail?>>
}
