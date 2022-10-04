package com.oguzel.travel_app.domain.model

data class TravelListModel(
    val errorMessage: String,
    val items: List<TravelModel>
)