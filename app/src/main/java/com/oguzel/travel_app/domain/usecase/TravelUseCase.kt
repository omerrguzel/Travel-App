package com.oguzel.travel_app.domain.usecase


import com.oguzel.travel_app.domain.model.BookmarkRequestModel
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

    fun getBookmarkedTravelInfo() =
        performNetworkOperation {
            travelRepository.getBookmarkedTravelInfo()
        }

    fun getTravelInfoByCategory(category: String) =
        performNetworkOperation {
            travelRepository.getTravelInfoByCategory(category)
        }

    fun getTravelById(id: String) =
        performNetworkOperation {
            travelRepository.getTravelById(id)
        }

    fun getGuideCategories() =
        performNetworkOperation {
            travelRepository.getGuideCategories()
        }

    fun updateBookmark(id: String, bookmarkRequestModel: BookmarkRequestModel) =
        performNetworkOperation {
            travelRepository.updateBookmark(id, bookmarkRequestModel)
        }
}
