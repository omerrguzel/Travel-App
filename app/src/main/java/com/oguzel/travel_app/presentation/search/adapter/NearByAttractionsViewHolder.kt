package com.oguzel.travel_app.presentation.search.adapter

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.ItemDealsBinding
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.search.SearchFragmentDirections
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.setMargins

class NearByAttractionsViewHolder(
    private val travelBinding: ViewDataBinding,
) : RecyclerView.ViewHolder(travelBinding.root) {
    fun onBind(travelModel : TravelModel) {
        val binding = travelBinding as ItemDealsBinding

        binding.apply {
            setVariable(BR.travelModel, travelModel)
            cardViewDeals.setMargins(top = 10, bottom = 10)
            textViewImageAmount.gone()
            textViewDuration.gone()
            textViewTitle.text = travelModel.city
            textViewType.text = travelModel.country
            cardViewDeals.layoutParams.width = 358
            cardViewDeals.layoutParams.height = 153
            if(travelModel.isBookmark){
                buttonBookmark.setIconResource(R.drawable.ic_bookmark_active)
            } else {
                buttonBookmark.setIconResource(R.drawable.ic_bookmark)
            }

            cardViewDeals.setOnClickListener {

                Navigation.findNavController(it)
                    .navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(travelModel.id))
            }
        }
    }
}