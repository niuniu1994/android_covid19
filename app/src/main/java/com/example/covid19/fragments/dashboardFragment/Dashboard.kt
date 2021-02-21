package com.example.covid19.fragments.dashboardFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19.CovidInfoActivity
import com.example.covid19.R
import com.example.covid19.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.view.*


class Dashboard : Fragment() {

    private lateinit var dashboardViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

       //RecycleView
        val adapter = DashboardAdapter(requireActivity())

        //navigate to covidinfo activity
        adapter.setOnItemClickListener{
            val intent = Intent(requireActivity(), CovidInfoActivity::class.java)
            intent.putExtra("country",it)
            requireActivity().startActivity(intent)
        }

        val recyclerView = view.recycleView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //ViewModel
        dashboardViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        dashboardViewModel.countrySubscriptions.observe(viewLifecycleOwner, Observer { data -> adapter.setCountryList(data) })
        return view



    }


}