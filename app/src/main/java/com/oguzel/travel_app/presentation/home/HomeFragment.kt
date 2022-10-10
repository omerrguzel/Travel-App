package com.oguzel.travel_app.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.FragmentHomeBinding
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.home.adapter.HomeDealsAdapter
import com.oguzel.travel_app.utils.Resource
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: HomeDealsAdapter = HomeDealsAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchTravelInfoByCategory("flight|hotel|transportation")
        initTab()
        initHomeButtons()
        logOut()
    }

    private fun fetchTravelInfoByCategory(category : String)  {
        viewModel.getTravelInfoByCategory(category).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                    println("Loading Info")
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    bindAdapter((it.data!!).shuffled())
                }
                Resource.Status.ERROR -> {
                    println("Fetch Info Error : ${it.message}")
                }
            }
        }
    }

    private fun bindAdapter(list : List<TravelModel>){
        adapter.setTravelList(list)
        binding.recyclerViewDeals.adapter=adapter
    }

    private fun initTab(){
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        fetchTravelInfoByCategory("flight|hotel|transportation")
                    }
                    1 -> {
                        fetchTravelInfoByCategory("flight")
                    }
                    2 -> {
                        fetchTravelInfoByCategory("hotel")
                    }
                    3 -> {
                        fetchTravelInfoByCategory("transportation")
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initHomeButtons(){
        binding.apply {
            flightHomeView.textView.text = context?.resources?.getText(R.string.flights)
            flightHomeView.button.setIconResource(R.drawable.ic_flight)
            flightHomeView.root.setOnClickListener {
                Toast.makeText(requireContext(),"Flights",Toast.LENGTH_SHORT).show()
            }
            hotelsHomeView.textView.text = context?.resources?.getText(R.string.hotels)
            hotelsHomeView.button.setIconResource(R.drawable.ic_hotels)
            hotelsHomeView.root.setOnClickListener {
                Toast.makeText(requireContext(),"Hotels",Toast.LENGTH_SHORT).show()
            }
            carsHomeView.textView.text = context?.resources?.getText(R.string.cars)
            carsHomeView.button.setIconResource(R.drawable.ic_cars)
            carsHomeView.root.setOnClickListener {
                Toast.makeText(requireContext(),"Cars",Toast.LENGTH_SHORT).show()
            }
            taxiHomeView.textView.text = context?.resources?.getText(R.string.taxi)
            taxiHomeView.button.setIconResource(R.drawable.ic_taxi)
            taxiHomeView.root.setOnClickListener {
                Toast.makeText(requireContext(),"Taxi",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logOut(){
        binding.buttonLogout.setOnClickListener{
            val action =
                HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
}