package com.oguzel.travel_app.presentation.searchresults

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.FragmentSearchResultBinding
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.searchresults.adapter.SearchResultsAdapter
import com.oguzel.travel_app.utils.Resource
import com.oguzel.travel_app.utils.searchModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private val viewModel: SearchResultViewModel by viewModels()
    private var searchAdapter: SearchResultsAdapter = SearchResultsAdapter(arrayListOf())
    private lateinit var tempList: List<TravelModel>
    private val navArgs: SearchResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchTravelInfo()
        backButtonController()

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
                    this.searchAdapter.setTravelList(
                        searchModel(navArgs.searchQuery, tempList)
                    )
                    binding.recyclerViewSearchResults.adapter = searchAdapter
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }

    private fun backButtonController() {
        binding.buttonBackSearchResults.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}