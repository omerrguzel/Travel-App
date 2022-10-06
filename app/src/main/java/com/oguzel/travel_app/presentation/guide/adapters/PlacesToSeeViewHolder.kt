package com.oguzel.travel_app.presentation.guide.adapters

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.ItemDealsBinding
import com.oguzel.travel_app.databinding.ItemPlacesToSeeBinding
import com.oguzel.travel_app.databinding.ItemPlacesToSeeBindingImpl
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.guide.GuideFragmentDirections
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.setMargins

class PlacesToSeeViewHolder(
    private val travelBinding: ViewDataBinding,
) : RecyclerView.ViewHolder(travelBinding.root) {
    fun onBind(travelModel : TravelModel) {
        val binding = travelBinding as ItemPlacesToSeeBinding

        binding.apply {
            setVariable(BR.travelModel, travelModel)

            if(travelModel.isBookmark){
                buttonBookmark.setIconResource(R.drawable.ic_bookmark_active)
            } else {
                buttonBookmark.setIconResource(R.drawable.ic_bookmark)
            }

            cardViewPlacesToSee.setOnClickListener {

                Navigation.findNavController(it)
                    .navigate(GuideFragmentDirections.actionGuideFragmentToDetailFragment(travelModel.id))
            }
        }
    }
}