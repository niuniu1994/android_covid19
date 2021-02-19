package com.example.covid19

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19.entity.Province
import com.example.covid19.viewmodel.CovidInfoViewModel
import kotlinx.android.synthetic.main.covidinfo_activity.*
import java.time.LocalDate
import java.util.*

class CovidInfoActivity : AppCompatActivity() {

    var date = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covidinfo_activity)
        val country = intent.getStringExtra("country")

        //adapter
        val adapter = CovidInfoAdapter()

        //recycleView
        val recyclerView = recycleView2
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //viewModel
        val viewModel = ViewModelProvider(this).get(CovidInfoViewModel::class.java)
        if (country != null) {
            viewModel.getProvinces(country,date.toString())
        }
        viewModel.provinces.observe(this, Observer { data -> adapter.setData(data)
        })

        previous_btn.setOnClickListener{
            if (date.compareTo(date.minusMonths(1)) > 0 && country != null){
                date = date.minusDays(1)
                viewModel.getProvinces(country,date.toString())
            }else{
                Toast.makeText(this,"Only last 30 days historical data is available",Toast.LENGTH_LONG)
            }
        }

        next_btn.setOnClickListener{
            if (date.compareTo(LocalDate.now()) < 0 && country != null){
                date = date.plusDays(1)
                viewModel.getProvinces(country,date.toString())
            }else{
                Toast.makeText(this,"Only last 30 days historical data is available",Toast.LENGTH_LONG)
            }
        }

    }

    fun initialization(provincesList: List<Province>){
        //RecycleView
//        val adapter = CovidInfoAdapter(provincesList)
//        adapter.setData(provincesList)

    }

}