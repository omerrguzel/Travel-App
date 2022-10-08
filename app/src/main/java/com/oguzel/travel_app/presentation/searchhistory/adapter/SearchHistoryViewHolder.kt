package com.oguzel.travel_app.presentation.searchhistory.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.ItemDropdownLineBinding
import com.oguzel.travel_app.utils.setMargins

class SearchHistoryViewHolder(
    private val travelBinding: ViewDataBinding,
) : RecyclerView.ViewHolder(travelBinding.root) {

    fun onBind(searchedQuery: String) {
        val binding = travelBinding as ItemDropdownLineBinding

        binding.apply {
            materialTextView.apply {
                text = searchedQuery
                setBackgroundResource(R.drawable.background_edittext_search)
                backgroundTintList = context.getColorStateList(R.color.pink)
                setMargins(
                    top = materialTextView.context.resources.getDimensionPixelSize(R.dimen._7sdp),
                    bottom = materialTextView.context.resources.getDimensionPixelSize(R.dimen._7sdp)
                )
            }
        }
    }
}