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
import com.oguzel.travel_app.databinding.FragmentSearchBinding
import com.oguzel.travel_app.domain.model.BookmarkRequestModel
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.search.adapter.NearByAttractionsAdapter
import com.oguzel.travel_app.presentation.search.adapter.TopDestinationsAdapter
import com.oguzel.travel_app.presentation.trip.adapters.BookmarksAdapter
import com.oguzel.travel_app.utils.Resource
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
        fetchTravelInfoByCategory("topdestination")
        fetchTravelInfoByCategory("nearby")
        navigateSearchHistory()
        navigateSearchResult()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.editTextSearchSearchScreen.setText("")
    }

    /**
     * fetchTravelInfoByCategory Fetches information from Api by category and calls for
     * related bindAdapter function on success
     * @param category Requested string to filter as category
     */
    private fun fetchTravelInfoByCategory(category: String) {
        viewModel.getTravelInfoByCategory(category).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                    println("Loading Info")
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    if (category == "topdestination") {
                        bindTopDestAdapter(it.data!!)
                    } else if (category == "nearby") {
                        bindNearByAdapter(it.data!!)
                    }
                }
                Resource.Status.ERROR -> {
                    println("Fetch Info Error : ${it.message}")
                }
            }
        }
    }

    /**
     * bindTopDestAdapter() binds given list to top destinations recyclerview adapter.
     * @param list Requested list to be bound to adapter.
     */
    private fun bindTopDestAdapter(list: List<TravelModel>) {
        topDestinationsAdapter.setTravelList(list)
        binding.recyclerViewTopDestinations.adapter = topDestinationsAdapter
    }

    /**
     * bindNearByAdapter() binds given list to nearby recyclerview adapter. Additionally
     * it initializes BookmarkClickListener which calls updateBookmark() method.
     * @param list Requested list to be bound to adapter.
     */
    private fun bindNearByAdapter(list: List<TravelModel>) {
        nearByAttractionsAdapter.setTravelList(list)
        binding.recyclerViewNearbyAttractions.adapter = nearByAttractionsAdapter
        nearByAttractionsAdapter.setOnItemClickListener(object :
            BookmarksAdapter.IBookmarkClickListener {
            override fun changeBookmarkState(id: String, isBookmark: Boolean) {
                updateBookmark(id, BookmarkRequestModel(!isBookmark))
            }
        })
    }

    /**
     * updateBookmark() uses PUT to send inverse of current isBookmark state to API.
     * @param id ID of travelModel to be bookmarked or to remove of its bookmark
     * @param isBookmark Current state of isBookmark.
     */
    private fun updateBookmark(id: String, isBookmark: BookmarkRequestModel) {
        viewModel.updateBookmark(id, isBookmark).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    fetchTravelInfoByCategory("nearby")
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }

    /**
     * To navigate to search history page
     */
    private fun navigateSearchHistory() {
        binding.buttonSearchHistory.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(SearchFragmentDirections.actionSearchFragmentToSearchHistoryFragment())
        }
    }

    /**
     * To navigate to search result screen
     */
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
