package com.oguzel.travel_app.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.oguzel.travel_app.data.local.sharedpref.model.SelectedTripModel


class SharedPrefManager {

    private var sharedPref: SharedPreferences? = null

    constructor(activity: Activity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
    }

    fun ifContains(key: String) : Boolean?{
        return sharedPref?.contains(key)
    }

    fun readDataString(key: String): Array<SelectedTripModel> {
        val gson = Gson()
        val jsonText: String = sharedPref?.getString(key, null).toString()
        val text = gson.fromJson(
            jsonText,
            Array<SelectedTripModel>::class.java
        )
        return text
    }

    fun writeDataString(key: String, value : Array<SelectedTripModel>): Boolean {
        val gson = Gson()
//        val value: List<String> = ArrayList(data)
        val jsonText = gson.toJson(value)
        sharedPref?.let {
            with(it.edit()) {
                putString(key, jsonText)
                apply()
                return commit()
            }
        }
        return false
    }
}