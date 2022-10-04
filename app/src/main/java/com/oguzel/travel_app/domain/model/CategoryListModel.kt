package com.oguzel.travel_app.domain.model

data class CategoryListModel(
    val errorMessage: String,
    val items: List<CategoryModel>
)