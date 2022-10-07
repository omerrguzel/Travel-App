package com.oguzel.travel_app.presentation.guide.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.domain.model.TravelModel

class PlacesToSeeAdapter(
    private var travelList: ArrayList<TravelModel> = ArrayList(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mListener : IBookmarkClickListener

    fun setOnItemClickListener(mListener : IBookmarkClickListener){
        this.mListener = mListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val travelBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_places_to_see, parent, false
        )
        return PlacesToSeeViewHolder(travelBinding,mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlacesToSeeViewHolder).onBind(travelList[position],mListener)
    }

    override fun getItemCount(): Int {
        return travelList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTravelList(travelList: List<TravelModel>) {
        this.travelList.clear()
        this.travelList.addAll(travelList)
        notifyDataSetChanged()
    }

    interface IBookmarkClickListener{
        fun changeBookmarkState(id : String, isBookmark : Boolean)
    }
}