package com.oguzel.travel_app.presentation.detail.adapter

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.ItemDetailImageBinding
import com.oguzel.travel_app.domain.model.ImageInfoModel
import com.oguzel.travel_app.utils.setMargins

class ImagesViewHolder(
    private val imageBinding: ViewDataBinding
) : RecyclerView.ViewHolder(imageBinding.root) {
    fun onBind(imageInfoModel: ImageInfoModel, mListener: ImagesAdapter.IImageClickListener) {
        val binding = imageBinding as ItemDetailImageBinding

        binding.apply {
            setVariable(BR.imageInfoModel, imageInfoModel)
            cardViewDetailImage.setMargins(
                left = cardViewDetailImage.context.resources.getDimensionPixelSize(R.dimen._2sdp),
                right = cardViewDetailImage.context.resources.getDimensionPixelSize(R.dimen._2sdp))

            cardViewDetailImage.setOnClickListener {
                mListener.changeImage(imageInfoModel)
            }
        }
    }
}