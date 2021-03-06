package com.example.covid19

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19.database.AppDatabase
import com.example.covid19.entity.Province
import com.example.covid19.repository.CountryRepository
import com.example.covid19.viewmodel.CovidInfoViewModel
import kotlinx.android.synthetic.main.covidinfo_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


/**
 * CovidInfoActivity occupied the layout of covidinfo_activity who shows the daily Covid data of different country
 */
class CovidInfoActivity : AppCompatActivity() {
    val cal = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    @RequiresApi(Build.VERSION_CODES.O)
    var date:Date = Date().also { cal.time = it; cal.add(Calendar.DATE,-30)}
    val oneMonthLimit = cal.time





    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covidinfo_activity)
        val country = intent.getStringExtra("country")
        val countryDao = AppDatabase.getDataBase(application).countryDao()
        val countryRepository = CountryRepository(countryDao)

        //adapter
        val adapter = CovidInfoAdapter()

        //recycleView
        val recyclerView = recycleView2
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //viewModel
        val viewModel = ViewModelProvider(this).get(CovidInfoViewModel::class.java)
        if (country != null) {
            viewModel.getProvinces(country,dateFormat.format(date))
            viewModel.getStatus(country)
            today_text.text = dateFormat.format(date)
        }

        viewModel.provinces.observe(this, Observer { data -> adapter.setData(data) })

        viewModel.status.observe(this, Observer {
            if (it == 1){
                subscribe_btn.text = "-"
            }
        })

        // jump to previous day
        previous_btn.setOnClickListener{


            if (date.compareTo(oneMonthLimit) > 0 && country != null){
                cal.time = date
                cal.add(Calendar.DATE, -1)

                date = cal.time
                today_text.text = dateFormat.format(date)
                viewModel.getProvinces(country,dateFormat.format(date))
            }else{
                Toast.makeText(this,"Only last 30 days historical data is available",Toast.LENGTH_LONG)
            }
        }

        // jump to next day
        next_btn.setOnClickListener{

            if (date.compareTo(Date()) < 0 && country != null){


                cal.time = date
                cal.add(Calendar.DATE, 1)

                date = cal.time
                today_text.text = dateFormat.format(date)
                viewModel.getProvinces(country,dateFormat.format(date))
            }else{
                Toast.makeText(this,"Only last 30 days historical data is available",Toast.LENGTH_LONG)
            }
        }

        subscribe_btn.setOnClickListener{
            if (country!= null){
                GlobalScope.launch(Dispatchers.IO) {
                    var status = countryRepository.getCountryStatusByName(country)
                    if (status == 0) status = 1 else status = 0
                    countryRepository.modifyCountryStatus(country,status)
                }
                val intent = Intent(this,MainActivity::class.java)
                this.startActivity(intent)
            }
        }

    }

}