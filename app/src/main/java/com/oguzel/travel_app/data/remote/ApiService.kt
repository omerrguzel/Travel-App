package com.oguzel.travel_app.data.remote

import com.oguzel.travel_app.domain.model.BookmarkRequestModel
import com.oguzel.travel_app.domain.model.CategoryModel
import com.oguzel.travel_app.domain.model.TravelModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("AllTravelList")
    suspend fun getTravelInfo() : Response<ArrayList<TravelModel>>

    @GET("AllTravelList?isBookmark=true")
    suspend fun getBookmarkedTravelInfo() : Response<ArrayList<TravelModel>>

    @GET("AllTravelList")
    suspend fun getTravelInfoByCategory(@Query("category") category : String) : Response<ArrayList<TravelModel>>

    @GET("AllTravelList/{id}")
    suspend fun getTravelById(@Path("id") id: String): Response<TravelModel>

    @GET("GuideCategories")
    suspend fun getGuideCategories(): Response<ArrayList<CategoryModel>>

    @PUT("AllTravelList/{id}")
    suspend fun updateBookmark(@Path("id") id : String, @Body bookmarkRequestModel: BookmarkRequestModel): Response<TravelModel>
}