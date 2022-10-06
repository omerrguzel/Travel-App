package com.oguzel.travel_app.domain.usecase


import com.oguzel.travel_app.domain.repository.TravelRepository
import com.oguzel.travel_app.utils.performNetworkOperation
import javax.inject.Inject

class TravelUseCase @Inject constructor(
    private val travelRepository: TravelRepository
) {

    fun getTravelInfo() =
        performNetworkOperation {
            travelRepository.getTravelInfo()
        }
    }
