package com.oguzel.travel_app.data.remote

import com.oguzel.travel_app.domain.model.BookmarkRequestModel
import com.oguzel.travel_app.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseDataSource() {

    suspend fun getTravelInfo() = getResult { apiService.getTravelInfo() }

    suspend fun getBookmarkedTravelInfo() = getResult { apiService.getBookmarkedTravelInfo() }

    suspend fun getTravelInfoByCategory(category : String) = getResult { apiService.getTravelInfoByCategory(category) }

    suspend fun getRestaurantById(id: String) = getResult { apiService.getTravelById(id) }

    suspend fun getGuideCategories() = getResult { apiService.getGuideCategories() }

    suspend fun updateBookmark(id : String , bookmarkRequestModel: BookmarkRequestModel) = getResult { apiService.updateBookmark(id,bookmarkRequestModel) }


}