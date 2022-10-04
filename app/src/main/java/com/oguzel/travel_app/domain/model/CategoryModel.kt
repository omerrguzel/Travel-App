package com.oguzel.travel_app.domain.model

import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String
)