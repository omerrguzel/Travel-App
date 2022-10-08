package com.oguzel.travel_app.presentation.searchhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.oguzel.travel_app.R
import com.oguzel.travel_app.data.local.room.SearchHistoryDatabase
import com.oguzel.travel_app.data.local.room.model.SearchHistoryModel
import com.oguzel.travel_app.databinding.FragmentSearchHistoryBinding
import com.oguzel.travel_app.presentation.searchhistory.adapter.SearchHistoryAdapter
import com.oguzel.travel_app.utils.hide
import com.oguzel.travel_app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchHistoryFragment : Fragment() {

    private lateinit var binding: FragmentSearchHistoryBinding
    private var searchHistoryDatabase: SearchHistoryDatabase? = null
    private var searchHistoryAdapter: SearchHistoryAdapter = SearchHistoryAdapter(arrayListOf())
    private var tempList: MutableList<String> = mutableListOf()
    private lateinit var searchedQueryList: ArrayList<SearchHistoryModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButtonController()
        fetchHistory()
        deleteSearchHistory()
    }

    private fun backButtonController() {
        binding.buttonBackSearchResults.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun fetchHistory() {
        searchHistoryDatabase = SearchHistoryDatabase.getSearchHistoryDatabase(requireContext())

        searchedQueryList = searchHistoryDatabase?.searchHistoryDao()
            ?.getSearchHistory() as ArrayList<SearchHistoryModel>
        searchedQueryList.forEach { i ->
            tempList.add(i.searchedText)
        }
        searchHistoryAdapter.setTravelList(tempList)
        binding.recyclerViewSearchHistory.adapter = searchHistoryAdapter
        binding.recyclerViewSearchHistory.show()
    }

    private fun deleteSearchHistory() {
        binding.buttonDeleteAll.setOnClickListener {
            searchedQueryList.forEach { i ->
                searchHistoryDatabase?.searchHistoryDao()?.delete(searchHistoryModel = i)
            }
            binding.recyclerViewSearchHistory.hide()
        }
    }

}