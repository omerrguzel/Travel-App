package com.oguzel.travel_app.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oguzel.travel_app.data.local.room.model.SearchHistoryModel

@Database(entities = [SearchHistoryModel::class], version = 1)
abstract class SearchHistoryDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDAO

    companion object {
        private var instance: SearchHistoryDatabase? = null

        fun getSearchHistoryDatabase(context: Context): SearchHistoryDatabase? {

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    SearchHistoryDatabase::class.java,
                    "searchHistoryModel.db"
                ).allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}