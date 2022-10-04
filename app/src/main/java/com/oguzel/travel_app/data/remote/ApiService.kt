package com.oguzel.travel_app.data.remote

import com.oguzel.travel_app.domain.model.CategoryListModel
import com.oguzel.travel_app.domain.model.TravelListModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("AllTravelList")
    fun getTravelInfo(): Call<TravelListModel>

    @GET("GuideCategories")
    fun getGuideCategories(): Call<CategoryListModel>
}