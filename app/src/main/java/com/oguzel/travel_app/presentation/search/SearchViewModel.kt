package com.oguzel.travel_app.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.oguzel.travel_app.domain.model.BookmarkRequestModel
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.domain.usecase.TravelUseCase
import com.oguzel.travel_app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val travelUseCase: TravelUseCase
) : ViewModel() {

    fun getTravelInfo() : LiveData<Resource<ArrayList<TravelModel>>> {
        return travelUseCase.getTravelInfo()
    }

    fun updateBookmark(
        id: String,
        bookmarkRequestModel: BookmarkRequestModel
    ): LiveData<Resource<TravelModel>> {
        return travelUseCase.updateBookmark(id, bookmarkRequestModel)
    }

    fun getTravelInfoByCategory(category : String) : LiveData<Resource<ArrayList<TravelModel>>> {
        return travelUseCase.getTravelInfoByCategory(category)
    }
}