package com.oguzel.travel_app.presentation.home.adapter


import android.util.TypedValue
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.ItemDealsBinding
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.home.HomeFragmentDirections
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.setMargins


class HomeDealsViewHolder(
    private val travelBinding: ViewDataBinding,
) : RecyclerView.ViewHolder(travelBinding.root) {

    /**
     * HomeDealsAdapter uses item_deals layout for each item. In this function we are defining
     * attributes of this item as desired.
     */
    fun onBind(travelModel: TravelModel) {
        val binding = travelBinding as ItemDealsBinding

        binding.apply {
            setVariable(BR.travelModel, travelModel)
            cardViewDeals.apply {
                setMargins(
                    left = context.resources.getDimensionPixelSize(R.dimen._7sdp),
                    right = context.resources.getDimensionPixelSize(R.dimen._7sdp)
                )
                layoutParams.width = context.resources.getDimensionPixelSize(R.dimen._200sdp)
                layoutParams.height = context.resources.getDimensionPixelSize(R.dimen._108sdp)
                setOnClickListener {

                    Navigation.findNavController(it)
                        .navigate(
                            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                                travelModel.id
                            )
                        )
                }
            }
            textViewCategory.gone()
            buttonBookmark.gone()
            textViewType.gone()
            textViewImageAmount.gone()
            textViewDuration.gone()
            textViewTitle.text = travelModel.title.split(",").first()
            textViewTitle.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                textViewTitle.context.resources.getDimension(R.dimen._19ssp)
            )
        }
    }
}