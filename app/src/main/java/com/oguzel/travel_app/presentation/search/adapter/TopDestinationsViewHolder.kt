package com.oguzel.travel_app.presentation.search.adapter


import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.databinding.ItemDealsBinding
import com.oguzel.travel_app.domain.model.TravelModel
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguzel.travel_app.R
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.setMargins
import com.oguzel.travel_app.presentation.search.SearchFragmentDirections


class TopDestinationsViewHolder(
    private val travelBinding: ViewDataBinding,
) : RecyclerView.ViewHolder(travelBinding.root) {
    fun onBind(travelModel : TravelModel) {
        val binding = travelBinding as ItemDealsBinding

        binding.apply {
            setVariable(BR.travelModel, travelModel)

            cardViewDeals.apply {
                setMargins(
                    left = cardViewDeals.context.resources.getDimensionPixelSize(R.dimen._7sdp),
                    right = cardViewDeals.context.resources.getDimensionPixelSize(R.dimen._7sdp))
                layoutParams.width = cardViewDeals.context.resources.getDimensionPixelSize(R.dimen._120sdp)
                layoutParams.height = cardViewDeals.context.resources.getDimensionPixelSize(R.dimen._155sdp)
                setOnClickListener {
                    Navigation.findNavController(it)
                        .navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(travelModel.id))
                }
            }

            textViewTitle.apply {
                text = travelModel.city
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    textViewTitle.context.resources.getDimension(R.dimen._12ssp)
                )
            }

            textViewType.text = travelModel.country
            imageViewDeals.scaleType = ImageView.ScaleType.CENTER_CROP

            textViewCategory.gone()
            buttonBookmark.gone()
            textViewImageAmount.gone()
            textViewDuration.gone()
//            linearLayoutTitleType.setMargins(bottom = 0)
        }
    }
}