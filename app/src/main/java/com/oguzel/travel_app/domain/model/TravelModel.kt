package com.oguzel.travel_app.domain.model

import com.google.gson.annotations.SerializedName

data class TravelModel(
    @SerializedName("category")
    val category: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val imageInfoModels: List<ImageInfoModel>,
    @SerializedName("isBookmark")
    val isBookmark: Boolean,
    @SerializedName("title")
    val title: String
)