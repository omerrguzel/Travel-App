package com.oguzel.travel_app.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.FragmentDetailBinding
import com.oguzel.travel_app.domain.model.BookmarkRequestModel
import com.oguzel.travel_app.domain.model.ImageInfoModel
import com.oguzel.travel_app.presentation.detail.adapter.ImagesAdapter
import com.oguzel.travel_app.utils.Resource
import com.oguzel.travel_app.utils.downloadFromUrl
import com.oguzel.travel_app.utils.gone
import com.oguzel.travel_app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val navArgs: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private var isBookmark: Boolean = false
    private var adapter: ImagesAdapter = ImagesAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchTravelInfo(navArgs.travelId)
        backButtonController()
        binding.buttonBookmarkDetail.setOnClickListener {
            updateBookmark(BookmarkRequestModel(!isBookmark))
        }

        adapter.setOnItemClickListener(object : ImagesAdapter.IImageClickListener {
            override fun changeImage(imageInfoModel: ImageInfoModel) {
                binding.imageViewDetail.downloadFromUrl(imageInfoModel.url)
            }
        })
    }

    private fun fetchTravelInfo(travelId: String) {
        viewModel.getTravelInfoDetail(travelId).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    binding.travelModel = it.data!!
                    isBookmark = it.data.isBookmark
                    adapter.setTravelList(it.data.imageInfoModels)
                    binding.recyclerViewDetailImages.adapter = adapter
                    changeBookmarkText(it.data.isBookmark)
                    binding.executePendingBindings()
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }

    private fun updateBookmark(isBookmark: BookmarkRequestModel) {
        viewModel.updateBookmark(navArgs.travelId, isBookmark).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    changeBookmarkText(it.data?.isBookmark)
                    fetchTravelInfo(navArgs.travelId)
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }

    private fun backButtonController() {
        binding.buttonBackDetail.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun changeBookmarkText(boolean: Boolean?) {
        if (boolean == true) {
            binding.buttonBookmarkDetail.text = getString(R.string.remove_bookmark)
        } else {
            binding.buttonBookmarkDetail.text = getString(R.string.add_bookmark)
        }
    }
}
