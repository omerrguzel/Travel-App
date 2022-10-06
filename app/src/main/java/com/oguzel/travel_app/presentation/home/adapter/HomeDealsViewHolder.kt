package com.oguzel.travel_app.presentation.home.adapter

import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.databinding.ItemDealsBinding
import com.oguzel.travel_app.domain.model.TravelModel
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import com.oguzel.travel_app.R
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.setMargins

//import com.oguzel.travel_app.presentation.home.HomeFragmentDirections


class HomeDealsViewHolder(
    private val travelBinding: ViewDataBinding,
) : RecyclerView.ViewHolder(travelBinding.root) {
    fun onBind(travelModel : TravelModel) {
        val binding = travelBinding as ItemDealsBinding

        binding.apply {
            setVariable(BR.travelModel, travelModel)
            cardViewDeals.setMargins(left = 10, right = 10)
            textViewCategory.gone()
            buttonBookmark.gone()
            textViewType.gone()
            textViewImageAmount.gone()
            textViewDuration.gone()
            cardViewDeals.layoutParams.width = 260
            cardViewDeals.layoutParams.height = 140
//            cardViewDeals.layoutParams.width = R.dimen._152sdp
//            cardViewDeals.layoutParams.height = R.dimen._117sdp

//            cardViewDeals.setOnClickListener {
//                Navigation.findNavController(it)
//                    .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(travelModel))
//            }
        }
    }


}