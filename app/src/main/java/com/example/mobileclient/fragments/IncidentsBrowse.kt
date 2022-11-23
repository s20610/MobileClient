package com.example.mobileclient.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentIncidentsBrowseBinding
import com.example.mobileclient.model.AccidentReport
import com.example.mobileclient.viewmodels.AccidentReportViewModel

class IncidentsBrowse : Fragment() {
    private var _binding: FragmentIncidentsBrowseBinding? = null
    private val binding get() = _binding!!
    private val accidentReportViewModel: AccidentReportViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentIncidentsBrowseBinding.inflate(inflater, container, false)
        val view = binding.root
        val userEmail: String =requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("email", "")!!
        accidentReportViewModel.getAccidentReports(userEmail)
//        val testAccidentReport = AccidentReport("test@test.pl", "1234", "CAR_ACCIDENT", 1, 1.0, 1.0, true, true)
//        val testList: ArrayList<AccidentReport> = ArrayList()
//        testList.add(testAccidentReport)
//        val arrayAdapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_list_item_1,
//            testList,
//        )
//        binding.listView.adapter = arrayAdapter
        accidentReportViewModel.getAccidentReportsResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    response.body()!!,
                )
                binding.listView.adapter = arrayAdapter
            }
        }

        binding.listView.setOnItemClickListener { adapterView, view, i, l ->
            accidentReportViewModel.pickedAccidentReport = adapterView.getItemAtPosition(i) as AccidentReport
            Navigation.findNavController(view)
                .navigate(R.id.action_incidentsBrowse_to_showIncident)
        }
        return view
    }
}