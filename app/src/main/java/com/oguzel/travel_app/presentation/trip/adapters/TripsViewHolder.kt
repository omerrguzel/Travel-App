package com.oguzel.travel_app.presentation.trip.adapters

import android.annotation.SuppressLint
import android.util.TypedValue
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.data.local.sharedpref.model.SelectedTripModel
import com.oguzel.travel_app.databinding.ItemTripBinding
import com.oguzel.travel_app.presentation.trip.TripFragmentDirections
import com.oguzel.travel_app.utils.setMargins

class TripsViewHolder(
    private val travelBinding: ViewDataBinding
) : RecyclerView.ViewHolder(travelBinding.root) {
    @SuppressLint("SetTextI18n")
    fun onBind(selectedTripModel: SelectedTripModel, mListener: TripsAdapter.IDeleteClickListener) {
        val binding = travelBinding as ItemTripBinding

        /**
         * TripsAdapter uses item_trip layout for each item. In this function we are
         * defining attributes of this item as desired. Additionally we are using a delete click
         * listener to communicate fragment with adapter.
         */
        binding.apply {
            setVariable(BR.selectedTripModel, selectedTripModel)
            cardViewTrip.apply {
                setMargins(
                    top = cardViewTrip.context.resources.getDimensionPixelSize(R.dimen._7sdp),
                    bottom = cardViewTrip.context.resources.getDimensionPixelSize(R.dimen._7sdp)
                )
                setOnClickListener {
                    Navigation.findNavController(it)
                        .navigate(
                            TripFragmentDirections.actionTripFragmentToDetailFragment(
                                selectedTripModel.travelModel.id
                            )
                        )
                }
            }
            textViewTitleTrip.setTextSize(
                TypedValue.COMPLEX_UNIT_PX, textViewTitleTrip.context.resources.getDimension(
                    R.dimen._19ssp
                )
            )
            buttonDeleteTrip.setOnClickListener {
                mListener.deleteTrip(selectedTripModel.travelModel.id)
            }
            val diff =
                (selectedTripModel.selectedArrivalDate - selectedTripModel.selectedDepartureDate) / MILLISECOND_IN_A_DAY
            textViewDurationTrip.text = diff.toString() + textViewDurationTrip.context.getString(R.string.days)
            textViewTypeTrip.text = selectedTripModel.selectedDates
            textViewImageAmountTrip.text =
                selectedTripModel.travelModel.imageInfoModels.size.toString() +
                        textViewImageAmountTrip.context.getString(R.string.images)
        }
    }

    companion object {
        const val MILLISECOND_IN_A_DAY = 86400000
    }
}