package com.oguzel.travel_app.presentation.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.FragmentGuideBinding
import com.oguzel.travel_app.domain.model.BookmarkRequestModel
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.guide.adapters.GuideCategoriesAdapter
import com.oguzel.travel_app.presentation.guide.adapters.MustSeeAdapter
import com.oguzel.travel_app.presentation.guide.adapters.PlacesToSeeAdapter
import com.oguzel.travel_app.presentation.trip.adapters.BookmarksAdapter
import com.oguzel.travel_app.utils.Resource
import com.oguzel.travel_app.utils.categorizeModel
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideFragment : Fragment() {

    private lateinit var binding : FragmentGuideBinding
    private val guideViewModel : GuideViewModel by viewModels()
    private var mustSeeAdapter: MustSeeAdapter = MustSeeAdapter(arrayListOf())
    private var placesToSeeAdapter : PlacesToSeeAdapter = PlacesToSeeAdapter(arrayListOf())
    private var guideCategoriesAdapter : GuideCategoriesAdapter = GuideCategoriesAdapter(arrayListOf())
    private lateinit var tempList : List<TravelModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_guide, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchTravelInfo()
        fetchCategoryInfo()
        placesToSeeAdapter.setOnItemClickListener(object : PlacesToSeeAdapter.IBookmarkClickListener{
            override fun changeBookmarkState(id: String, isBookmark: Boolean) {
                updateBookmark(id, BookmarkRequestModel(!isBookmark))
            }
        })

        binding.editTextSearchGuideScreen.text = null
        binding.buttonSearchGuideScreen.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(GuideFragmentDirections.actionGuideFragmentToSearchResultFragment(binding.editTextSearchGuideScreen.text.toString()))
        }
    }

    override fun onViewStateRestored(@Nullable savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.editTextSearchGuideScreen.setText("")
    }

    private fun fetchTravelInfo() {
        guideViewModel.getTravelInfo().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    tempList = it.data!!
                    this.mustSeeAdapter.setTravelList(
                        categorizeModel("mightneed",tempList)
                    )
                    binding.recyclerViewMightNeedThese.adapter = mustSeeAdapter

                    placesToSeeAdapter.setTravelList(
                        categorizeModel("toppick",tempList)
                    )
                    binding.recyclerViewPlacesToGo.adapter = placesToSeeAdapter
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }

    private fun fetchCategoryInfo() {
        guideViewModel.getGuideCategories().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    guideCategoriesAdapter.setCategoryList(it.data!!)
                    binding.recyclerViewCategoryChips.adapter = guideCategoriesAdapter
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }

    private fun updateBookmark(id : String, isBookmark: BookmarkRequestModel) {
        guideViewModel.updateBookmark(id , isBookmark).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    fetchTravelInfo()
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }
}