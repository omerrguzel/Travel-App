package com.oguzel.travel_app.utils

import com.oguzel.travel_app.data.local.sharedpref.model.SelectedTripModel
import com.oguzel.travel_app.domain.model.TravelModel

fun categorizeModel(categoryFilter: String, arrayList: List<TravelModel>): List<TravelModel> {
    val tempArrayList = arrayListOf<TravelModel>()
    arrayList.forEach { i ->
        when (i.category) {
            categoryFilter -> tempArrayList.add(i)
        }
    }
    return tempArrayList
}

fun dropDownFilterModel(arrayList: List<TravelModel>): List<String> {
    val tempArrayList = arrayListOf<String>()
    arrayList.forEach { i ->
        when (i.category) {
            "topdestination" -> tempArrayList.add(i.title)
            "nearby" -> tempArrayList.add(i.title)
            "toppick" -> tempArrayList.add(i.title)
            "mightneed" -> tempArrayList.add(i.title)
            "hotel" -> tempArrayList.add(i.title)
        }
    }
    return tempArrayList
}

fun findTravelModelByTitle(title: String, arrayList: List<TravelModel>): List<TravelModel> {
    val tempArrayList = arrayListOf<TravelModel>()
    arrayList.forEach { i ->
        when (i.title) {
            title -> tempArrayList.add(i)
        }
    }
    return tempArrayList
}

fun searchModel(
    searchQuery: String, arrayList: List<TravelModel>
): List<TravelModel> {
    val tempArrayList = arrayListOf<TravelModel>()
    arrayList.forEach { data ->
        if (data.title.contains(searchQuery) || data.description.contains(searchQuery)) {
            tempArrayList.add(data)
        }
    }
    return tempArrayList
}

fun bookmarkCheckModel(
    arrayList: List<TravelModel>
): List<TravelModel> {
    val tempArrayList = arrayListOf<TravelModel>()
    arrayList.forEach { data ->
        if (data.isBookmark) {
            tempArrayList.add(data)
        }
    }
    return tempArrayList
}

fun removeTripModelByID(
    id: String, list: List<SelectedTripModel>
): MutableList<SelectedTripModel> {
    val tempList = mutableListOf<SelectedTripModel>()
    list.forEach { i ->
        if (i.travelModel.id != id) {
            tempList.add(i)
        }
    }
    return tempList
}

