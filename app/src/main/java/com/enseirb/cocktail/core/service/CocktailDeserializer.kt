package com.enseirb.cocktail.core.service

import com.enseirb.cocktail.core.model.Cocktail
import com.enseirb.cocktail.core.model.Ingredient
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CocktailDeserializer : JsonDeserializer<Cocktail> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Cocktail {
        val jsonObject = json?.asJsonObject
        val id = jsonObject?.get("idDrink")?.asInt
        val name = jsonObject?.get("strDrink")?.asString
        val category = jsonObject?.get("strCategory")?.asString
        val alcoholic = jsonObject?.get("strAlcoholic")?.asString
        val recipe = jsonObject?.get("strInstructions")?.asString
        val thumb = jsonObject?.get("strDrinkThumb")?.asString
        val ingredients = mutableListOf<Ingredient>()

        if (jsonObject?.get("strIngredient1") != null) {
            for (i in 1..15) {
                if (jsonObject?.get("strIngredient$i")!!.isJsonNull)
                    break
                val measure = when {
                    jsonObject?.get("strMeasure$i")!!.isJsonNull -> null
                    else -> jsonObject?.get("strMeasure$i")!!.asString
                }
                ingredients.add(Ingredient(jsonObject?.get("strIngredient$i")!!.asString,  measure))
            }
        }

        return Cocktail(id, name, category, alcoholic, ingredients, recipe, thumb)
    }
}