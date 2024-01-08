package com.enseirb.cocktail.core.service

import com.enseirb.cocktail.core.model.Cocktail
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    val gson = GsonBuilder()
        .registerTypeAdapter(Cocktail::class.java, CocktailDeserializer())
        .create()

    val service: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}