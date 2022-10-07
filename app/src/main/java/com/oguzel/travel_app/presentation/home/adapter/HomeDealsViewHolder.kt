package com.oguzel.travel_app.presentation.home.adapter


import android.content.res.Resources
import android.util.TypedValue
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.databinding.ItemDealsBinding
import com.oguzel.travel_app.domain.model.TravelModel
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import com.oguzel.travel_app.R
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.setMargins
import com.oguzel.travel_app.presentation.home.HomeFragmentDirections


class HomeDealsViewHolder(
    private val travelBinding: ViewDataBinding,
) : RecyclerView.ViewHolder(travelBinding.root) {
    fun onBind(travelModel : TravelModel) {
        val binding = travelBinding as ItemDealsBinding

        binding.apply {
            setVariable(BR.travelModel, travelModel)
            cardViewDeals.setMargins(
                left = cardViewDeals.context.resources.getDimensionPixelSize(R.dimen._7sdp),
                right = cardViewDeals.context.resources.getDimensionPixelSize(R.dimen._7sdp))
            textViewCategory.gone()
            buttonBookmark.gone()
            textViewType.gone()
            textViewImageAmount.gone()
            textViewDuration.gone()
            textViewTitle.text = travelModel.title.split(",").first()
            textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,textViewTitle.context.resources.getDimension(R.dimen._19ssp))
            cardViewDeals.layoutParams.width = cardViewDeals.context.resources.getDimensionPixelSize(R.dimen._200sdp)
            cardViewDeals.layoutParams.height = cardViewDeals.context.resources.getDimensionPixelSize(R.dimen._108sdp)

            cardViewDeals.setOnClickListener {

                Navigation.findNavController(it)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(travelModel.id))
            }
        }
    }


}