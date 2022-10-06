package com.oguzel.travel_app.data.remote

import com.oguzel.travel_app.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseDataSource() {

    suspend fun getTravelInfo() = getResult { apiService.getTravelInfo() }

}