package com.oguzel.travel_app.presentation.search

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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private var topDestinationsAdapter: TopDestinationsAdapter = TopDestinationsAdapter(arrayListOf())
    private var nearByAttractionsAdapter : NearByAttractionsAdapter = NearByAttractionsAdapter(arrayListOf())
    private lateinit var tempList : List<TravelModel>
    private var searchHistoryDatabase : SearchHistoryDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchHistoryDatabase  = SearchHistoryDatabase.getSearchHistoryDatabase(requireContext())

        fetchTravelInfo()

        nearByAttractionsAdapter.setOnItemClickListener(object : BookmarksAdapter.IBookmarkClickListener{
            override fun changeBookmarkState(id: String, isBookmark: Boolean) {
                updateBookmark(id, BookmarkRequestModel(!isBookmark))
            }
        })

        binding.buttonSearchHistory.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(SearchFragmentDirections.actionSearchFragmentToSearchHistoryFragment())
        }
        binding.buttonSearchSearchScreen.setOnClickListener {

            var result = ""

            val searchedQueryList: ArrayList<SearchHistoryModel> =
                searchHistoryDatabase?.searchHistoryDao()?.getSearchHistory() as ArrayList<SearchHistoryModel>

            searchedQueryList.forEach {

                println(it.searchedText+"\n")
                result += it.searchedText

            }


            Navigation.findNavController(it)
                .navigate(SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(binding.editTextSearchSearchScreen.text.toString()))
        }
    }


    override fun onViewStateRestored(@Nullable savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.editTextSearchSearchScreen.setText("")
    }

    private fun fetchTravelInfo() {
        viewModel.getTravelInfo().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.SUCCESS -> {
                    tempList = it.data!!
                    println("Success!!!")
                    this.topDestinationsAdapter.setTravelList(
                        categorizeModel("topdestination",tempList))
                    binding.recyclerViewTopDestinations.adapter = topDestinationsAdapter

                    nearByAttractionsAdapter.setTravelList(
                        categorizeModel("nearby",tempList))
                    binding.recyclerViewNearbyAttractions.adapter = nearByAttractionsAdapter
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }

    private fun updateBookmark(id : String, isBookmark: BookmarkRequestModel) {
        viewModel.updateBookmark(id , isBookmark).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    println("UpdateBookmark Successful")
                    fetchTravelInfo()
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }
}
