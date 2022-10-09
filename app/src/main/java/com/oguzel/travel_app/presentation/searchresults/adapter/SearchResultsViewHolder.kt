package com.oguzel.travel_app.presentation.searchresults.adapter

import android.util.TypedValue
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
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
            cardViewDeals.apply {
                setMargins(
                    top = context.resources.getDimensionPixelSize(R.dimen._10sdp),
                    bottom = context.resources.getDimensionPixelSize(R.dimen._10sdp)
                )
                layoutParams.width = cardViewDeals.context.resources.getDimensionPixelSize(R.dimen._275sdp)
                layoutParams.height = cardViewDeals.context.resources.getDimensionPixelSize(R.dimen._117sdp)
                setOnClickListener {
                    Navigation.findNavController(it)
                        .navigate(SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(travelModel.id))
                }
            }

            textViewTitle.apply {
                text = travelModel.title.split(",").first()
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    textViewTitle.context.resources.getDimension(R.dimen._19ssp)
                )
            }
            textViewCategory.gone()
            buttonBookmark.gone()
            textViewType.gone()
            textViewImageAmount.gone()
            textViewDuration.gone()
        }
    }


}