package com.oguzel.travel_app.presentation

import android.app.DatePickerDialog
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
import com.google.android.material.button.MaterialButton
import com.oguzel.travel_app.R
import com.oguzel.travel_app.databinding.BottomSheetTripBinding
import com.oguzel.travel_app.domain.model.SelectedTripModel
import com.oguzel.travel_app.presentation.trip.TripViewModel
import com.oguzel.travel_app.utils.Resource
import com.oguzel.travel_app.utils.SharedPrefManager
import com.oguzel.travel_app.utils.dropDownFilterModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TripBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetTripBinding
    private val viewModel: TripViewModel by viewModels()
    private lateinit var selectedTitle: String
    private lateinit var selectedDepartureDate: String
    private lateinit var selectedArrivalDate: String
    private lateinit var sharedPrefManager: SharedPrefManager
    private var selectedTripList: MutableList<SelectedTripModel> = mutableListOf()


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
            selectedDepartureDate = openDatePickerDialog(binding.buttonDepartureDate)
        }

        binding.buttonArrivalDate.setOnClickListener {
            selectedArrivalDate = openDatePickerDialog(binding.buttonArrivalDate)
        }

        binding.buttonSave.setOnClickListener {
            println(selectedDepartureDate + selectedArrivalDate + selectedTitle)
            if (sharedPrefManager.ifContains(PATIKA) == true) {
                selectedTripList = sharedPrefManager.readDataString(PATIKA).toMutableList()
            }

            selectedTripList.add(
                SelectedTripModel(
                    selectedTitle = selectedTitle,
                    selectedDepartureDate = selectedDepartureDate,
                    selectedArrivalDate = selectedArrivalDate
                )
            )
            sharedPrefManager.writeDataString(PATIKA, selectedTripList.toTypedArray())
        }

        binding.buttonRead.setOnClickListener {
            if (sharedPrefManager.ifContains(PATIKA) == true) {
                println(sharedPrefManager.readDataString(PATIKA).toList())
            } else
                println("Shared Pref is empty")
        }


//        binding.dropMenuText.onItemSelectedListener(object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                val item = p0?.getItemAtPosition(p2).toString()
//                println(item)
//                Toast.makeText(requireContext(),"$p2 th item selected",Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//
//        })

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

    private fun updateDatePickerButton(
        myCalendar: Calendar,
        materialButton: MaterialButton
    ): String {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ROOT)
        val selectedDate = sdf.format(myCalendar.time)
        materialButton.text = selectedDate
        return selectedDate
    }

    private fun openDatePickerDialog(materialButton: MaterialButton): String {
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        DatePickerDialog(
            requireActivity(),
            datePicker,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
        return updateDatePickerButton(myCalendar, materialButton)
    }

    companion object {
        const val PATIKA = "PATIKA"
    }
}

private fun AutoCompleteTextView.onItemSelectedListener(onItemSelectedListener: AdapterView.OnItemSelectedListener) {

}
