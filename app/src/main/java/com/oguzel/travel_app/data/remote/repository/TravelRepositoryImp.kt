package com.oguzel.travel_app.data.remote.repository

import com.oguzel.travel_app.data.remote.ApiService
import com.oguzel.travel_app.domain.model.TravelListModel
import com.oguzel.travel_app.domain.repository.TravelRepository
import retrofit2.Call

class TravelRepositoryImp(private val apiService: ApiService) : TravelRepository {

    override fun getTravelInfo(): Call<TravelListModel> {
        return apiService.getTravelInfo()
    }
}