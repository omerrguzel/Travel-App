package com.oguzel.travel_app.presentation.guide.adapters

import android.util.TypedValue
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.ItemDealsBinding
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.guide.GuideFragmentDirections
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.setMargins


class MustSeeViewHolder(
    private val travelBinding: ViewDataBinding,
) : RecyclerView.ViewHolder(travelBinding.root) {
    fun onBind(travelModel: TravelModel) {
        val binding = travelBinding as ItemDealsBinding

        binding.apply {
            setVariable(BR.travelModel, travelModel)
            cardViewDeals.apply {
                setMargins(
                    left = context.resources.getDimensionPixelSize(R.dimen._7sdp),
                    right = context.resources.getDimensionPixelSize(R.dimen._7sdp)
                )
                layoutParams.width = context.resources.getDimensionPixelSize(R.dimen._85sdp)
                layoutParams.height = context.resources.getDimensionPixelSize(R.dimen._96sdp)
                setOnClickListener {

                    Navigation.findNavController(it)
                        .navigate(
                            GuideFragmentDirections.actionGuideFragmentToDetailFragment(
                                travelModel.id
                            )
                        )
                }
            }
            textViewImageAmount.apply {
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    textViewTitle.context.resources.getDimension(R.dimen._10ssp)
                )
                typeface = ResourcesCompat.getFont(
                    textViewImageAmount.context,
                    R.font.source_sans_pro_bold
                )
                text = travelModel.city
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    textViewTitle.context.resources.getDimension(R.dimen._15ssp)
                )
                setCompoundDrawables(null, null, null, null)
            }
            imageViewDeals.scaleType = ImageView.ScaleType.CENTER_CROP
            textViewDuration.gone()
            buttonBookmark.gone()
            textViewCategory.gone()
            textViewType.gone()
            textViewTitle.gone()
        }
    }
}