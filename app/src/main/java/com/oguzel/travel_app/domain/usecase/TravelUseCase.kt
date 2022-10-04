package com.oguzel.travel_app.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oguzel.travel_app.domain.model.TravelListModel
import com.oguzel.travel_app.domain.repository.TravelRepository
import javax.inject.Inject

class TravelUseCase @Inject constructor(
    private val travelRepository: TravelRepository
) {
    private var _travelInfo = MutableLiveData<TravelListModel>()
    val travelInfo : LiveData<TravelListModel> = _travelInfo

    fun getTravelInfo(){

    }
}