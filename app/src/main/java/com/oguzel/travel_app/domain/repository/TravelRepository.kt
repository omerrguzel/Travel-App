package com.oguzel.travel_app.domain.repository

import com.oguzel.travel_app.domain.model.BookmarkRequestModel
import com.oguzel.travel_app.domain.model.CategoryModel
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.utils.Resource


interface TravelRepository {

    suspend fun getTravelInfo(): Resource<ArrayList<TravelModel>>

    suspend fun getTravelById(id : String): Resource<TravelModel>

    suspend fun getGuideCategories() : Resource<ArrayList<CategoryModel>>

    suspend fun updateBookmark(id : String , bookmarkRequestModel: BookmarkRequestModel) : Resource<TravelModel>

}