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
            cardViewDeals.setMargins(left = 10, right = 10)
            textViewCategory.gone()
            buttonBookmark.gone()
            textViewImageAmount.gone()
            textViewDuration.gone()
//            val newCity : ArrayList<String> = travelModel.city.split(", ").toTypedArray()
//            textViewTitle.text = textViewTitle.toString().split(",").first()
            linearLayoutTitleType.setMargins(bottom = 0)
            textViewTitle.text = travelModel.city
            textViewType.text = travelModel.country
            imageViewDeals.scaleType = ImageView.ScaleType.CENTER_CROP
            cardViewDeals.layoutParams.width = 150
            cardViewDeals.layoutParams.height = 200
            textViewTitle.textSize = 15F
//            textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, R.dimen._20ssp)
//            cardViewDeals.layoutParams.width = R.dimen._152sdp
//            cardViewDeals.layoutParams.height = R.dimen._117sdp

            cardViewDeals.setOnClickListener {

                Navigation.findNavController(it)
                    .navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(travelModel.id))
            }
        }
    }
}