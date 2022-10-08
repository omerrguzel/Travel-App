package com.oguzel.travel_app.presentation.trip.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.data.local.sharedpref.model.SelectedTripModel

class TripsAdapter(
    private var selectedTripList: ArrayList<SelectedTripModel> = ArrayList(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mListener : IDeleteClickListener

    fun setOnItemClickListener(mListener : IDeleteClickListener){
        this.mListener = mListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val travelBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_trip, parent, false
        )
        return TripsViewHolder(travelBinding,mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TripsViewHolder).onBind(selectedTripList[position],mListener)
    }

    override fun getItemCount(): Int {
        return selectedTripList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTravelList(travelList: List<SelectedTripModel>){
        this.selectedTripList.clear()
        this.selectedTripList.addAll(travelList)
        notifyDataSetChanged()
    }

    interface IDeleteClickListener{
        fun deleteTrip(id : String)
    }
}