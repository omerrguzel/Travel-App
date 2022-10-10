package com.oguzel.travel_app.presentation.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.oguzel.travel_app.R
import com.oguzel.travel_app.data.local.sharedpref.model.SelectedTripModel
import com.oguzel.travel_app.databinding.FragmentTripBinding
import com.oguzel.travel_app.domain.model.BookmarkRequestModel
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.trip.adapters.BookmarksAdapter
import com.oguzel.travel_app.presentation.trip.adapters.TripsAdapter
import com.oguzel.travel_app.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripFragment : Fragment() {

    private lateinit var binding: FragmentTripBinding
    private val viewModel: TripViewModel by viewModels()
    private var bookmarksAdapter: BookmarksAdapter = BookmarksAdapter(arrayListOf())
    private lateinit var sharedPrefManager: SharedPrefManager
    private var selectedTripList: MutableList<SelectedTripModel> = mutableListOf()
    private var tripsAdapter: TripsAdapter = TripsAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTripPage()
        initSelectedTab()
        refreshData()

    }

    private fun initTripPage() {

        sharedPrefManager = SharedPrefManager(this.requireActivity())
        binding.apply {
            fobAddTrip.show()
            recyclerViewTripsBookmarks.show()
            progressBar.gone()
            fobAddTrip.setOnClickListener {
                openBottomSheet()
            }
        }
        if (sharedPrefManager.ifContains(TripBottomSheetFragment.PATIKA) == true) {
            selectedTripList = sharedPrefManager.readDataString(
                TripBottomSheetFragment.PATIKA
            ).toMutableList()

            tripsAdapter.setOnItemClickListener(object :
                TripsAdapter.IDeleteClickListener {
                override fun deleteTrip(id: String) {
                    sharedPrefManager.writeDataString(
                        TripBottomSheetFragment.PATIKA,
                        removeTripModelByID(id, selectedTripList).toTypedArray()
                    )
                    initTripPage()
                }
            })
            binding.recyclerViewTripsBookmarks.show()
            tripsAdapter.setTravelList(selectedTripList)
            binding.recyclerViewTripsBookmarks.adapter = tripsAdapter
        }
        else
            binding.recyclerViewTripsBookmarks.hide()
    }

    private fun initSelectedTab() {
        binding.recyclerViewTripsBookmarks.show()
        binding.progressBar.gone()
        binding.tabLayoutTrips.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        initTripPage()
                    }
                    1 -> {
                        binding.fobAddTrip.gone()
                        fetchTravelInfo()
                        bookmarksAdapter.setOnItemClickListener(object :
                            BookmarksAdapter.IBookmarkClickListener {
                            override fun changeBookmarkState(id: String, isBookmark: Boolean) {
                                updateBookmark(id, BookmarkRequestModel(!isBookmark))
                            }
                        })
                        binding.executePendingBindings()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun bindBookmarkAdapter(list: List<TravelModel>){
        bookmarksAdapter.setTravelList(list)
        binding.recyclerViewTripsBookmarks.adapter = bookmarksAdapter
    }

    fun fetchTravelInfo() {
        viewModel.getBookmarkedTravelInfo().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    bindBookmarkAdapter(it.data!!)
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
                    binding.progressBar.gone()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.show()
                    fetchTravelInfo()
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }

    private fun openBottomSheet() {
        val bottomSheetFragment = TripBottomSheetFragment()
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

    private fun refreshData() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                recyclerViewTripsBookmarks.hide()
                progressBar.hide()
                if(binding.tabLayoutTrips.selectedTabPosition==0){
                    initTripPage()
                }
                initSelectedTab()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}