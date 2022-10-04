package com.oguzel.travel_app.domain.model

import com.google.gson.annotations.SerializedName

data class ImageInfoModel(
    @SerializedName("altText")
    val altText: Any,
    @SerializedName("height")
    val height: Int,
    @SerializedName("isHeroImage")
    val isHeroImage: Boolean,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)