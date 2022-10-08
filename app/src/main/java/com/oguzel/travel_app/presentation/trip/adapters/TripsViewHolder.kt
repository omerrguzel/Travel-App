package com.oguzel.travel_app.presentation.trip.adapters

import android.util.TypedValue
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.ItemTripBinding
import com.oguzel.travel_app.data.local.sharedpref.model.SelectedTripModel
import com.oguzel.travel_app.presentation.trip.TripFragmentDirections
import com.oguzel.travel_app.utils.setMargins

class TripsViewHolder(
    private val travelBinding: ViewDataBinding,
    mListener: TripsAdapter.IDeleteClickListener
) : RecyclerView.ViewHolder(travelBinding.root) {
    fun onBind(selectedTripModel: SelectedTripModel, mListener: TripsAdapter.IDeleteClickListener) {
        val binding = travelBinding as ItemTripBinding

        binding.apply {
            setVariable(BR.selectedTripModel, selectedTripModel)
            cardViewTrip.setMargins(
                top = cardViewTrip.context.resources.getDimensionPixelSize(R.dimen._7sdp),
                bottom = cardViewTrip.context.resources.getDimensionPixelSize(R.dimen._7sdp)
            )
            textViewTitleTrip.setTextSize(
                TypedValue.COMPLEX_UNIT_PX, textViewTitleTrip.context.resources.getDimension(
                    R.dimen._19ssp
                )
            )

            buttonDeleteTrip.setOnClickListener {
                mListener.deleteTrip(selectedTripModel.travelModel.id)
            }

            textViewDurationTrip.text =
                ((selectedTripModel.selectedArrivalDate - selectedTripModel.selectedDepartureDate) / 86400000).toString()

            println("selected Arrival Date" + selectedTripModel.selectedArrivalDate)
            println((selectedTripModel.selectedArrivalDate - selectedTripModel.selectedDepartureDate))

            textViewTypeTrip.text = selectedTripModel.selectedDates

            textViewImageAmountTrip.text =
                selectedTripModel.travelModel.imageInfoModels.size.toString() + " Images"

            cardViewTrip.setOnClickListener {

                Navigation.findNavController(it)
                    .navigate(
                        TripFragmentDirections.actionTripFragmentToDetailFragment(
                            selectedTripModel.travelModel.id
                        )
                    )
            }
        }
    }
}