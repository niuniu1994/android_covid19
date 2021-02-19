package com.example.covid19

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19.entity.Province
import com.example.covid19.viewmodel.CovidInfoViewModel
import kotlinx.android.synthetic.main.covidinfo_activity.*

class CovidInfoActivity : AppCompatActivity() {

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
            viewModel.getProvinces(country)
        }
        viewModel.provinces.observe(this, Observer { data -> adapter.setData(data)
        })



//        AsyncForProvincesList(this,country!!).execute()
    }

    fun initialization(provincesList: List<Province>){
        //RecycleView
//        val adapter = CovidInfoAdapter(provincesList)
//        adapter.setData(provincesList)

    }

}