package com.oguzel.travel_app.data.remote

import com.oguzel.travel_app.domain.model.CategoryListModel
import com.oguzel.travel_app.domain.model.CategoryModel
import com.oguzel.travel_app.domain.model.TravelModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("AllTravelList")
    suspend fun getTravelInfo() : Response<ArrayList<TravelModel>>

    @GET("AllTravelList/{id}")
    suspend fun getTravelById(@Path("id") id: String): Response<TravelModel>

    @GET("GuideCategories")
    suspend fun getGuideCategories(): Response<ArrayList<CategoryModel>>
}