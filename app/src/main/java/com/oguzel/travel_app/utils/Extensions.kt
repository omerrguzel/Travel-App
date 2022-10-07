package com.oguzel.travel_app.utils

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oguzel.travel_app.R
import com.oguzel.travel_app.domain.model.TravelModel

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.setMargins(
    left: Int = this.marginLeft,
    top: Int = this.marginTop,
    right: Int = this.marginRight,
    bottom: Int = this.marginBottom,
) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(left, top, right, bottom)
    }
}

fun ImageView.downloadFromUrl(img_src: String?) {

    img_src?.let {
        Glide.with(context)
            .load(img_src)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}

/**
 * @param img_src marsDataModelden gelen img_src değerlerini view yardımıyla göstermemizi sağlıyor.
 */
@BindingAdapter("android:downloadImageUrl")
fun downloadImage(view: ImageView, img_src: String?) {
    view.downloadFromUrl(img_src)
}

fun categorizeModel(categoryFilter: String, arrayList: List<TravelModel>)
        : List<TravelModel> {
    val tempArrayList = arrayListOf<TravelModel>()
    arrayList.forEach { i ->
        when (i.category) {
            categoryFilter -> tempArrayList.add(i)
        }
    }
    return tempArrayList
}

fun searchModel(
    searchQuery : String,
    arrayList: List<TravelModel>
) : List<TravelModel>{
    val tempArrayList = arrayListOf<TravelModel>()
    arrayList.forEach { data ->
        if(data.title.contains(searchQuery) || data.description.contains(searchQuery)) {
            tempArrayList.add(data)
        }
    }
    return tempArrayList
}

fun bookmarkCheckModel(
    arrayList: List<TravelModel>
) : List<TravelModel>{
    val tempArrayList = arrayListOf<TravelModel>()
    arrayList.forEach { data ->
        if(data.isBookmark) {
            tempArrayList.add(data)
        }
    }
    return tempArrayList
}
