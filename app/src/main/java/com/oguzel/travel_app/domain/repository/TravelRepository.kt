package com.oguzel.travel_app.domain.repository

import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.utils.Resource


interface TravelRepository {

    suspend fun getTravelInfo(): Resource<ArrayList<TravelModel>>
}