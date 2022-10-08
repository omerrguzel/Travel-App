package com.oguzel.travel_app.data.local.sharedpref.model

import com.oguzel.travel_app.domain.model.TravelModel

data class SelectedTripModel(
    val selectedDates : String,
    val selectedDepartureDate : Long,
    val selectedArrivalDate : Long,
    val travelModel: TravelModel
)
