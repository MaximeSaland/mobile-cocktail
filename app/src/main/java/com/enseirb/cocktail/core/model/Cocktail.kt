package com.enseirb.cocktail.core.model

data class Cocktail(
    val id: Int?,
    val name: String?,
    val category: String?,
    val alcoholic: String?,
    val ingredients: List<Ingredient>?,
    val recipe: String?
)