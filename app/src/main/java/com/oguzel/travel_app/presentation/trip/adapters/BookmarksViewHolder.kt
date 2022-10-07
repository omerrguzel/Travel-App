package com.oguzel.travel_app.presentation.trip.adapters

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.ItemDealsBinding
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.trip.TripFragmentDirections
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.setMargins

class BookmarksViewHolder(
    private val travelBinding: ViewDataBinding,
) : RecyclerView.ViewHolder(travelBinding.root) {
    fun onBind(travelModel : TravelModel) {
        val binding = travelBinding as ItemDealsBinding

        binding.apply {
            setVariable(BR.travelModel, travelModel)
            cardViewDeals.setMargins(top = 10, bottom = 10)
            textViewCategory.gone()
            textViewImageAmount.gone()
            textViewDuration.gone()
            textViewTitle.text = travelModel.city
            textViewTitle.textSize = 20F
            textViewType.text = travelModel.country
            textViewType.textSize = 15F
            imageViewDeals.scaleType = ImageView.ScaleType.CENTER_CROP
            cardViewDeals.layoutParams.width = 358
            cardViewDeals.layoutParams.height = 153
            if(travelModel.isBookmark){
                buttonBookmark.setIconResource(R.drawable.ic_bookmark_active)
                buttonBookmark.setIconTintResource(R.color.pink)
            } else {
                buttonBookmark.setIconResource(R.drawable.ic_bookmark)
            }

            cardViewDeals.setOnClickListener {

                Navigation.findNavController(it)
                    .navigate(TripFragmentDirections.actionTripFragmentToDetailFragment(travelModel.id))
            }
        }
    }
}