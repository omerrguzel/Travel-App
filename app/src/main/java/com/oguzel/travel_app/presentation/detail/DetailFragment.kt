package com.oguzel.travel_app.presentation.detail

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
import com.oguzel.travel_app.databinding.FragmentDetailBinding
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var dataBinding: FragmentDetailBinding
    private val navArgs: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchTravelInfo(navArgs.travelId)
        backButtonController()
    }

    fun fetchTravelInfo(travelId: String) {
        viewModel.getTravelInfoDetail(travelId).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.SUCCESS -> {
                    println(it.data)
                    dataBinding.travelModel = it.data!!
                    dataBinding.executePendingBindings()
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }

    private fun backButtonController() {
        dataBinding.buttonBackDetail.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
