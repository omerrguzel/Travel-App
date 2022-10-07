package com.oguzel.travel_app.presentation.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.trip.adapters.BookmarksAdapter

class NearByAttractionsAdapter(
    private var travelList: ArrayList<TravelModel> = ArrayList(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mListener : BookmarksAdapter.IBookmarkClickListener

    fun setOnItemClickListener(mListener : BookmarksAdapter.IBookmarkClickListener){
        this.mListener = mListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val travelBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_deals, parent, false
        )
        return NearByAttractionsViewHolder(travelBinding,mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NearByAttractionsViewHolder).onBind(travelList[position],mListener)
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

        fun changeBookmarkAppearance(){

        }
    }
}