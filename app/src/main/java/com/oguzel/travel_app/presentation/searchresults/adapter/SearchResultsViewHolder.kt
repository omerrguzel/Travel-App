package com.oguzel.travel_app.presentation.searchresults.adapter

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.databinding.ItemDealsBinding
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.searchresults.SearchResultFragmentDirections
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.setMargins

class SearchResultsViewHolder(
    private val travelBinding: ViewDataBinding,
) : RecyclerView.ViewHolder(travelBinding.root) {
    fun onBind(travelModel : TravelModel) {
        val binding = travelBinding as ItemDealsBinding

        binding.apply {
            setVariable(BR.travelModel, travelModel)
            cardViewDeals.setMargins(top = 10, bottom = 10)
            textViewCategory.gone()
            buttonBookmark.gone()
            textViewType.gone()
            textViewImageAmount.gone()
            textViewDuration.gone()
            textViewTitle.text = travelModel.title.split(",").first()
            textViewTitle.textSize = 20F
            cardViewDeals.layoutParams.width = 300
            cardViewDeals.layoutParams.height = 200

            cardViewDeals.setOnClickListener {

                Navigation.findNavController(it)
                    .navigate(SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(travelModel.id))
            }
        }
    }


}