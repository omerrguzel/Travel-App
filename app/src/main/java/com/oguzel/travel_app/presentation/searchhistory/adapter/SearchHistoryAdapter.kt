package com.oguzel.travel_app.presentation.searchhistory.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R

class SearchHistoryAdapter(
    private var searchHistoryList: ArrayList<String> = ArrayList(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val searchBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_dropdown_line, parent, false
        )
        return SearchHistoryViewHolder(searchBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchHistoryViewHolder).onBind(searchHistoryList[position])
    }

    override fun getItemCount(): Int {
        return searchHistoryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTravelList(searchHistoryList: MutableList<String>) {
        this.searchHistoryList.clear()
        this.searchHistoryList.addAll(searchHistoryList)
        notifyDataSetChanged()
    }
}