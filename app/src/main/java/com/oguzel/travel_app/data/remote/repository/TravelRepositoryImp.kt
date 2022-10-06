package com.oguzel.travel_app.data.remote.repository

import com.oguzel.travel_app.data.remote.RemoteDataSource
import com.oguzel.travel_app.domain.model.CategoryModel
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.domain.repository.TravelRepository
import com.oguzel.travel_app.utils.Resource
import javax.inject.Inject

class TravelRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource) : TravelRepository {

    override suspend fun getTravelInfo() : Resource<ArrayList<TravelModel>> {
        return remoteDataSource.getTravelInfo()
    }

    override suspend fun getTravelById(id: String): Resource<TravelModel> {
        return remoteDataSource.getRestaurantById(id)
    }

    override suspend fun getGuideCategories(): Resource<ArrayList<CategoryModel>> {
        return remoteDataSource.getGuideCategories()
    }
}