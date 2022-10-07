package com.oguzel.travel_app.domain.model

import com.google.gson.annotations.SerializedName

data class BookmarkRequestModel(
    @SerializedName("isBookmark")
    val isBookmark: Boolean
)
