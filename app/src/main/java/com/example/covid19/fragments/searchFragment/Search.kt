package com.example.covid19.fragments.searchFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19.CovidInfoActivity
import com.example.covid19.R
import com.example.covid19.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_search.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

class Search : Fragment() {

    private lateinit var searchViewModel: SharedViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        //RecycleView
        val adapter = SearchAdapter(requireActivity())

        //navigate to covidinfo activity
        adapter.setOnItemClickListener{
            val intent = Intent(requireActivity(), CovidInfoActivity::class.java)
            intent.putExtra("country",it)
            requireActivity().startActivity(intent)
        }

        val recyclerView = view.recycleView1
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //ViewModel
        searchViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        // clear list
        searchViewModel.countryList.postValue(emptyList())
        // observe the data
        searchViewModel.countryList.observe(this, Observer { data ->
            adapter.setData(data)
        })


        // text changed listener for countryNameInput
        view.countryNameInput.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var name = view.countryNameInput.text.toString()
                if (!name.isEmpty()) searchViewModel.countryNameChanged(name) else searchViewModel.countryList.postValue(
                    emptyList())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })




        return view
    }


}