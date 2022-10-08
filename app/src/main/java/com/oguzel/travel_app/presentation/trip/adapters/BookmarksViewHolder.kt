package com.oguzel.travel_app.presentation.trip.adapters

import android.util.TypedValue
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
    mListener: BookmarksAdapter.IBookmarkClickListener
) : RecyclerView.ViewHolder(travelBinding.root) {
    fun onBind(travelModel: TravelModel, mListener: BookmarksAdapter.IBookmarkClickListener) {
        val binding = travelBinding as ItemDealsBinding

        binding.apply {
            setVariable(BR.travelModel, travelModel)
            cardViewDeals.apply {
                setMargins(
                    top = context.resources.getDimensionPixelSize(R.dimen._7sdp),
                    bottom = context.resources.getDimensionPixelSize(R.dimen._7sdp))
                layoutParams.width = context.resources.getDimensionPixelSize(R.dimen._275sdp)
                layoutParams.height = context.resources.getDimensionPixelSize(R.dimen._154sdp)
                setOnClickListener {
                    Navigation.findNavController(it)
                        .navigate(TripFragmentDirections.actionTripFragmentToDetailFragment(travelModel.id))
                }
            }

            buttonBookmark.apply {
                if (travelModel.isBookmark) {
                    setIconResource(R.drawable.ic_bookmark_active)
                    setIconTintResource(R.color.pink)
                } else {
                    setIconResource(R.drawable.ic_bookmark)
                }
                setOnClickListener {
                    mListener.changeBookmarkState(travelModel.id, travelModel.isBookmark)
                }
            }

            textViewTitle.apply {
                text = travelModel.city
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    textViewTitle.context.resources.getDimension(R.dimen._19ssp))
            }

            textViewType.apply {
                text = travelModel.country
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    textViewType.context.resources.getDimension(R.dimen._12ssp)
                )
            }

            textViewCategory.gone()
            textViewImageAmount.gone()
            textViewDuration.gone()
            imageViewDeals.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }
}
