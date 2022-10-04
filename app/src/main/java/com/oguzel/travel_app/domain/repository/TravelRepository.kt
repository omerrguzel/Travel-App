package com.oguzel.travel_app.domain.repository

import com.oguzel.travel_app.domain.model.TravelListModel
import retrofit2.Call

interface TravelRepository {

    fun getTravelInfo(): Call<TravelListModel>
}