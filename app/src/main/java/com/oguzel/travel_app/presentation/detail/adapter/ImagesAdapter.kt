package com.oguzel.travel_app.presentation.detail.adapter

import android.annotation.SuppressLint
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.domain.model.ImageInfoModel
import com.oguzel.travel_app.presentation.trip.adapters.BookmarksAdapter


class ImagesAdapter(
    private var imageList: ArrayList<ImageInfoModel> = ArrayList(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mListener : ImagesAdapter.IImageClickListener

    fun setOnItemClickListener(mListener : ImagesAdapter.IImageClickListener){
        this.mListener = mListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val travelBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_detail_image, parent, false
        )
        return ImagesViewHolder(travelBinding,mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImagesViewHolder).onBind(imageList[position],mListener)

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTravelList(imageList: List<ImageInfoModel>) {
        this.imageList.clear()
        this.imageList.addAll(imageList)
        notifyDataSetChanged()
    }

    interface IImageClickListener{
        fun changeImage(imageInfoModel: ImageInfoModel)
    }

}
