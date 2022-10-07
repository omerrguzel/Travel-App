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
import com.oguzel.travel_app.presentation.trip.adapters.BookmarksAdapter
import com.oguzel.travel_app.utils.Resource
import com.oguzel.travel_app.utils.bookmarkCheckModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripFragment : Fragment() {

    private lateinit var binding: FragmentTripBinding
    private val viewModel: TripViewModel by viewModels()
    private var bookmarksAdapter: BookmarksAdapter = BookmarksAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTab()
    }

    private fun initTab() {
        binding.tabLayoutTrips.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {}
                    1 -> {
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
}