package com.oguzel.travel_app.presentation.trip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.BottomSheetTripBinding
import com.oguzel.travel_app.data.local.sharedpref.model.SelectedTripModel
import com.oguzel.travel_app.domain.model.TravelModel
import com.oguzel.travel_app.presentation.trip.TripViewModel
import com.oguzel.travel_app.utils.Resource
import com.oguzel.travel_app.utils.SharedPrefManager
import com.oguzel.travel_app.utils.dropDownFilterModel
import com.oguzel.travel_app.utils.findTravelModelByTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetTripBinding
    private val viewModel: TripViewModel by viewModels()
    private lateinit var selectedTitle: String
    private var selectedDepartureDate: Long = 0
    private var selectedArrivalDate: Long = 0
    private lateinit var selectedDates : String
    private lateinit var sharedPrefManager: SharedPrefManager
    private var selectedTripList: MutableList<SelectedTripModel> = mutableListOf()
    private lateinit var selectedTripModel : TravelModel
    private lateinit var findList: MutableList<TravelModel>


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

        sharedPrefManager = SharedPrefManager(this.requireActivity())
        fetchTravelInfo()


        binding.buttonDepartureDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
            datePicker.show(parentFragmentManager, "DatePicker")

            datePicker.addOnPositiveButtonClickListener {
                selectedDates = datePicker.headerText
                selectedDepartureDate = datePicker.selection?.first!!
                selectedArrivalDate = datePicker.selection?.second!!
            }

        }

        binding.buttonSave.setOnClickListener {
            if (sharedPrefManager.ifContains(PATIKA) == true) {
                selectedTripList = sharedPrefManager.readDataString(PATIKA).toMutableList()
                dialog?.dismiss()
            }

            selectedTripList.add(
                SelectedTripModel(
                    selectedDates = selectedDates,
                    selectedDepartureDate = selectedDepartureDate,
                    selectedArrivalDate = selectedArrivalDate,
                    travelModel =selectedTripModel
                )
            )
            sharedPrefManager.writeDataString(PATIKA, selectedTripList.toTypedArray())
        }


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
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        }
                        Resource.Status.SUCCESS -> {
                            if(findTravelModelByTitle(selectedTitle,it.data!!).size != 0){
                                selectedTripModel = findTravelModelByTitle(selectedTitle,it.data!!).get(0)

                            }
                            println("selected trip model is" +selectedTripModel)
                        }
                        Resource.Status.ERROR -> {
                            println(it.message)
                        }
                    }
                }
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

//    private fun updateDatePickerButton(
//        myCalendar: Calendar,
//        materialButton: MaterialButton
//    ): String {
//        val myFormat = "dd/MM/yyyy"
//        val sdf = SimpleDateFormat(myFormat, Locale.ROOT)
//        val selectedDate = sdf.format(myCalendar.time)
//        materialButton.text = selectedDate
//        return selectedDate
//    }

//    private fun openDatePickerDialog(materialButton: MaterialButton): String {
//        val myCalendar = Calendar.getInstance()
//        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//            myCalendar.set(Calendar.YEAR, year)
//            myCalendar.set(Calendar.MONTH, month)
//            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//        }
//
//        DatePickerDialog(
//            requireActivity(),
//            datePicker,
//            myCalendar.get(Calendar.YEAR),
//            myCalendar.get(Calendar.MONTH),
//            myCalendar.get(Calendar.DAY_OF_MONTH)
//        ).show()
//        return updateDatePickerButton(myCalendar, materialButton)
//    }

    companion object {
        const val PATIKA = "PATIKA"
    }
}

private fun AutoCompleteTextView.onItemSelectedListener(onItemSelectedListener: AdapterView.OnItemSelectedListener) {

}
