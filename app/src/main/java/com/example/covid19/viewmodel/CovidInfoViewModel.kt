package com.example.covid19.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covid19.database.AppDatabase
import com.example.covid19.entity.Country
import com.example.covid19.entity.Province
import com.example.covid19.repository.CountryRepository
import com.squareup.okhttp.Dispatcher
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.InputStream
import java.lang.Exception
import java.net.URL
import java.time.LocalDate
import javax.net.ssl.HttpsURLConnection
import kotlin.concurrent.thread

/**
 * We use this viewmodel only in CovidInfoActivity
 */
class CovidInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val countryRepository: CountryRepository

    var status = MutableLiveData<Int>()

    var provinces = MutableLiveData<List<Province>>()

    init {
        val countryDao = AppDatabase.getDataBase(application).countryDao()
        countryRepository = CountryRepository(countryDao)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getProvinces(country: String, date: String) {

        GlobalScope.launch {
            val client = OkHttpClient()
            var jsonObject: JSONObject? = null

            val request = Request.Builder()
                .url("https://api.covid19api.com/live/country/$country")
                .get()
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body().string()
                var data = "{ provinces : $body }"
                jsonObject = JSONObject(data)
            }

            if (jsonObject != null) {
                var provincesList = mutableListOf<Province>()

                val jsonArray = jsonObject.getJSONArray("provinces")
                for (index in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(index)
                    if (jsonObject.getString("Date").substring(0, 10).equals(date)) {
                        val provinceName = jsonObject.getString("Province")
                        val active = jsonObject.getInt("Active")
                        val recovered = jsonObject.getInt("Recovered")
                        val confirmed = jsonObject.getInt("Confirmed")
                        val deaths = jsonObject.getInt("Deaths")
                        val province =
                            Province(provinceName, confirmed, recovered, deaths, active)
                        provincesList.add(province)
                    }
                }
                provinces.postValue(provincesList)
            }
        }

    }

    fun getStatus(country: String) {
        GlobalScope.launch(Dispatchers.IO) {
            status.postValue(countryRepository.getCountryStatusByName(country))
        }

    }
}