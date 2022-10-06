package com.oguzel.travel_app.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.FragmentHomeBinding
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.home.adapter.HomeDealsAdapter
import com.oguzel.travel_app.utils.Resource
import com.oguzel.travel_app.utils.categorizeModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: HomeDealsAdapter = HomeDealsAdapter(arrayListOf())
    private lateinit var tempList : List<TravelModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchTravelInfo()
    }


    fun fetchTravelInfo() {
        viewModel.getTravelInfo().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.SUCCESS -> {
                    tempList = it.data!!
                    initTab(tempList.shuffled())
                    adapter.setTravelList((
                            categorizeModel("flight",tempList)+
                            categorizeModel("hotel",tempList)+
                            categorizeModel("transportation",tempList)).shuffled())
                    binding.recyclerViewDeals.adapter = adapter
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }


    fun initTab(list: List<TravelModel>){
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        adapter.setTravelList((
                                    categorizeModel("flight",tempList)+
                                    categorizeModel("hotel",tempList)+
                                    categorizeModel("transportation",tempList)).shuffled())
                        binding.recyclerViewDeals.adapter = adapter
                    }
                    1 -> {
                        adapter.setTravelList(categorizeModel("flight",tempList))
                        binding.recyclerViewDeals.adapter = adapter
                    }
                    2 -> {
                        adapter.setTravelList(categorizeModel("hotel",tempList))
                        binding.recyclerViewDeals.adapter = adapter
                    }
                    3 -> {
                        adapter.setTravelList(categorizeModel("transportation",tempList))
                        binding.recyclerViewDeals.adapter = adapter
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

}