package com.oguzel.travel_app.presentation.guide.adapters

import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
import androidx.core.widget.TextViewCompat.AutoSizeTextType
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
    fun onBind(travelModel : TravelModel) {
        val binding = travelBinding as ItemDealsBinding

        binding.apply {
            setVariable(BR.travelModel, travelModel)
            cardViewDeals.setMargins(left = 5, right = 5)
            textViewDuration.gone()
            buttonBookmark.gone()
            textViewCategory.gone()
            textViewType.gone()
            textViewTitle.gone()
            textViewImageAmount.apply {
                setAutoSizeTextTypeUniformWithConfiguration(15,25,1,TypedValue.COMPLEX_UNIT_SP)
                typeface= ResourcesCompat.getFont(textViewImageAmount.context,R.font.source_sans_pro_bold)
                text = travelModel.city
                textSize = 20F
                setCompoundDrawables(null,null,null,null)
            }
            imageViewDeals.scaleType = ImageView.ScaleType.CENTER_CROP
            cardViewDeals.layoutParams.width = 108
            cardViewDeals.layoutParams.height = 125


            cardViewDeals.setOnClickListener {

                Navigation.findNavController(it)
                    .navigate(GuideFragmentDirections.actionGuideFragmentToDetailFragment(travelModel.id))
            }
        }
    }
}