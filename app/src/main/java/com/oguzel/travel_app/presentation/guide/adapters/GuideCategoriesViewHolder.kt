package com.oguzel.travel_app.presentation.guide.adapters

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.oguzel.travel_app.databinding.ItemGuideCategoriesBinding
import com.oguzel.travel_app.domain.model.CategoryModel
import com.oguzel.travel_app.utils.setMargins


class GuideCategoriesViewHolder(
    private val binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(categoryModel: CategoryModel) {
        val binding = binding as ItemGuideCategoriesBinding

        binding.apply {
            setVariable(BR.categoryModel, categoryModel)

            chipItem.setMargins(left = 5, right = 5)
        }
    }
}