package com.enseirb.cocktail.core.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(@SerializedName("drinks", alternate = ["ingredients"]) val data: List<T>)
