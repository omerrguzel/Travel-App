package com.oguzel.travel_app.presentation.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.domain.model.TravelModel

class HomeDealsAdapter(
    private var travelList: ArrayList<TravelModel> = ArrayList(),
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val travelBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_deals, parent, false
        )
        return HomeDealsViewHolder(travelBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeDealsViewHolder).onBind(travelList[position])
    }

    override fun getItemCount(): Int {
        return travelList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTravelList(travelList: List<TravelModel>){
        this.travelList.clear()
        this.travelList.addAll(travelList)
        notifyDataSetChanged()
    }
}