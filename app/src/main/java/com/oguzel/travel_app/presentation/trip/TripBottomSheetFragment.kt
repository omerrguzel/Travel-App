package com.oguzel.travel_app.presentation.trip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.oguzel.travel_app.R
import com.oguzel.travel_app.data.local.sharedpref.model.SelectedTripModel
import com.oguzel.travel_app.databinding.BottomSheetTripBinding
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetTripBinding
    private val viewModel: TripViewModel by viewModels()
    private lateinit var selectedTitle: String
    private var selectedDepartureDate: Long = 0
    private var selectedArrivalDate: Long = 0
    private lateinit var selectedDates: String
    private lateinit var sharedPrefManager: SharedPrefManager
    private var selectedTripList: MutableList<SelectedTripModel> = mutableListOf()
    private lateinit var selectedTripModel: TravelModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.bottom_sheet_trip,
            null,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchTravelInfo()
        openDatePicker()
        saveButtonClickListener()
        dropDownMenuListener()
    }

    /**
     * openDatePicker() opens Material Date Picker component
     */
    private fun openDatePicker() {
        binding.buttonSelectTripDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
            datePicker.show(parentFragmentManager, "DatePicker")

            datePicker.addOnPositiveButtonClickListener {
                selectedDates = datePicker.headerText
                selectedDepartureDate = datePicker.selection?.first!!
                selectedArrivalDate = datePicker.selection?.second!!
            }
        }
    }

    /**
     * saveButtonClickListener() saves selected title and date as SelectedTripModel to sharedPref
     *
     */
    private fun saveButtonClickListener() {
        sharedPrefManager = SharedPrefManager(this.requireActivity())
        binding.buttonSave.setOnClickListener {
            if (sharedPrefManager.ifContains(PATIKA) == true) {
                selectedTripList = sharedPrefManager.readDataString(PATIKA).toMutableList()
            }
            dialog?.dismiss()
            selectedTripList.add(
                SelectedTripModel(
                    selectedDates = selectedDates,
                    selectedDepartureDate = selectedDepartureDate,
                    selectedArrivalDate = selectedArrivalDate,
                    travelModel = selectedTripModel
                )
            )
            sharedPrefManager.writeDataString(PATIKA, selectedTripList.toTypedArray())
        }
    }

    /**
     * dropDownMenuListener().onTextChanged() method is used for listening selected trip title
     * and search in API then save it to selectedTripModel.
     */
    private fun dropDownMenuListener() {
        binding.dropMenuText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                selectedTitle = s.toString()

                viewModel.getTravelInfo().observe(viewLifecycleOwner) {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            binding.progressBar.show()
                        }
                        Resource.Status.SUCCESS -> {
                            binding.progressBar.gone()
                            if (findTravelModelByTitle(selectedTitle, it.data!!).isNotEmpty()) {
                                selectedTripModel =
                                    findTravelModelByTitle(selectedTitle, it.data)[0]
                            }
                        }
                        Resource.Status.ERROR -> {
                            println(it.message)
                        }
                    }
                }
            }
        })
    }

    /**
     * fetchTravelInfo() fetches all travelModels from API and binds it to dropDownMenu adapter
     */
    private fun fetchTravelInfo() {
        viewModel.getTravelInfo().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    val adapter = ArrayAdapter(
                        requireContext(),
                        R.layout.item_dropdown_line,
                        dropDownFilterModel(it.data!!)
                    )
                    (binding.dropMenuText as? AutoCompleteTextView)?.setAdapter(adapter)
                }
                Resource.Status.ERROR -> {
                    println(it.message)
                }
            }
        }
    }

    companion object {
        const val PATIKA = "PATIKA"
    }
}
