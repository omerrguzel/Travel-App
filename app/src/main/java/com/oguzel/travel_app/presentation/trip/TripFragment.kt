package com.oguzel.travel_app.presentation.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.FragmentTripBinding
import com.oguzel.travel_app.domain.model.BookmarkRequestModel
import com.oguzel.travel_app.data.local.sharedpref.model.SelectedTripModel
import com.oguzel.travel_app.presentation.trip.TripBottomSheetFragment
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

        sharedPrefManager = SharedPrefManager(this.requireActivity())
        initTripTab()
        initTab()
        refreshData()

        binding.fobAddTrip.setOnClickListener {
            openBottomSheet()
            onPause()
        }

    }

    private fun initTab() {
        binding.tabLayoutTrips.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        initTripTab()
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

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    fun fetchTravelInfo() {
        viewModel.getTravelInfo().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.SUCCESS -> {
                    bookmarksAdapter.setTravelList(bookmarkCheckModel(it.data!!))
                    binding.recyclerViewTripsBookmarks.adapter = bookmarksAdapter
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

    private fun openBottomSheet() {


        val bottomSheetFragment = TripBottomSheetFragment()
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)

    }

    private fun initTripTab() {
        binding.fobAddTrip.show()
        binding.recyclerViewTripsBookmarks.show()
        binding.tripLoading.gone()

        if (sharedPrefManager.ifContains(TripBottomSheetFragment.PATIKA) == true) {
            println(sharedPrefManager.readDataString(TripBottomSheetFragment.PATIKA).toList())
            selectedTripList = sharedPrefManager.readDataString(
                TripBottomSheetFragment.PATIKA
            ).toMutableList()

            tripsAdapter.setOnItemClickListener(object :
                TripsAdapter.IDeleteClickListener{
                override fun deleteTrip(id: String) {
                    sharedPrefManager.writeDataString(TripBottomSheetFragment.PATIKA, removeTripModelByID(id,selectedTripList).toTypedArray())
                    initTripTab()
                }

            })

            tripsAdapter.setTravelList(selectedTripList)
            binding.recyclerViewTripsBookmarks.adapter = tripsAdapter
        } else
            println("Shared Pref is empty")
    }

    private fun refreshData() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                recyclerViewTripsBookmarks.hide()
                tripLoading.hide()
                initTripTab()
                initTab()
                swipeRefreshLayout.isRefreshing = false
            }

        }
    }
}