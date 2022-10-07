package com.oguzel.travel_app.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.oguzel.travel_app.domain.model.BookmarkRequestModel
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.domain.usecase.TravelUseCase
import com.oguzel.travel_app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val travelUseCase: TravelUseCase,
) : ViewModel() {

    fun getTravelInfoDetail(id: String): LiveData<Resource<TravelModel>> {
        return travelUseCase.getTravelById(id)
    }

    fun updateBookmark(
        id: String,
        bookmarkRequestModel: BookmarkRequestModel
    ): LiveData<Resource<TravelModel>> {
        return travelUseCase.updateBookmark(id, bookmarkRequestModel)
    }
}