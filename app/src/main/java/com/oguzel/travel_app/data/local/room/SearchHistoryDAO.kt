package com.oguzel.travel_app.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.oguzel.travel_app.data.local.room.model.SearchHistoryModel

@Dao
interface SearchHistoryDAO {

    @Insert
    fun insert(searchHistoryModel: SearchHistoryModel)

    @Delete
    fun delete(searchHistoryModel: SearchHistoryModel)

    @Query("SELECT*FROM searchHistoryModel")
    fun getSearchHistory(): List<SearchHistoryModel>
}