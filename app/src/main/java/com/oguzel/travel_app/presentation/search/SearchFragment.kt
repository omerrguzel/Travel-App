package com.oguzel.travel_app.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.oguzel.travel_app.R
import com.oguzel.travel_app.data.local.room.SearchHistoryDatabase
import com.oguzel.travel_app.data.local.room.model.SearchHistoryModel
import com.oguzel.travel_app.databinding.FragmentSearchBinding
import com.oguzel.travel_app.domain.model.BookmarkRequestModel
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.search.adapter.NearByAttractionsAdapter
import com.oguzel.travel_app.presentation.search.adapter.TopDestinationsAdapter
import com.oguzel.travel_app.presentation.trip.adapters.BookmarksAdapter
import com.oguzel.travel_app.utils.Resource
import com.oguzel.travel_app.utils.categorizeModel
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private var topDestinationsAdapter: TopDestinationsAdapter =
        TopDestinationsAdapter(arrayListOf())
    private var nearByAttractionsAdapter: NearByAttractionsAdapter =
        NearByAttractionsAdapter(arrayListOf())
    private lateinit var tempList: List<TravelModel>
    private var searchHistoryDatabase: SearchHistoryDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchHistoryDatabase = SearchHistoryDatabase.getSearchHistoryDatabase(requireContext())

        fetchTravelInfo()
        navigateSearchHistory()
        navigateSearchResult()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.editTextSearchSearchScreen.setText("")
    }

    private fun fetchTravelInfo() {
        viewModel.getTravelInfo().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    tempList = it.data!!
                    this.topDestinationsAdapter.setTravelList(
                        categorizeModel("topdestination", tempList)
                    )
                    binding.recyclerViewTopDestinations.adapter = topDestinationsAdapter

                    nearByAttractionsAdapter.setTravelList(
                        categorizeModel("nearby", tempList)
                    )
                    binding.recyclerViewNearbyAttractions.adapter = nearByAttractionsAdapter
                    nearByAttractionsAdapter.setOnItemClickListener(object :
                        BookmarksAdapter.IBookmarkClickListener {
                        override fun changeBookmarkState(id: String, isBookmark: Boolean) {
                            updateBookmark(id, BookmarkRequestModel(!isBookmark))
                        }
                    })
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }

    private fun updateBookmark(id: String, isBookmark: BookmarkRequestModel) {
        viewModel.updateBookmark(id, isBookmark).observe(viewLifecycleOwner) {
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

    private fun navigateSearchHistory(){
        binding.buttonSearchHistory.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(SearchFragmentDirections.actionSearchFragmentToSearchHistoryFragment())
        }
    }

    private fun navigateSearchResult() {
        binding.buttonSearchSearchScreen.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(
                    SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(
                        binding.editTextSearchSearchScreen.text.toString()
                    )
                )
        }
    }
}
