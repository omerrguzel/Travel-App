package com.oguzel.travel_app.data.remote

import com.oguzel.travel_app.domain.model.CategoryListModel
import com.oguzel.travel_app.domain.model.TravelModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("AllTravelList")
    suspend fun getTravelInfo() : Response<ArrayList<TravelModel>>

    @GET("GuideCategories")
    suspend fun getGuideCategories(): Response<CategoryListModel>
}