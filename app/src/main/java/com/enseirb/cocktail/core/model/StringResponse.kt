package com.enseirb.cocktail.core.model

import com.google.gson.annotations.SerializedName

data class StringResponse(@SerializedName("strCategory", alternate = ["strIngredient1"]) val name: String)
