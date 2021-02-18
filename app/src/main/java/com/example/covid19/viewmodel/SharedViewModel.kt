package com.example.covid19.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.covid19.database.AppDatabase
import com.example.covid19.entity.Country
import com.example.covid19.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for dashboard fragment
 */
class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val countryRepository: CountryRepository

    //subscription country list
    var countryList = MutableLiveData<List<Country>>()

    //subscription country list
    var countrySubscriptions: LiveData<List<Country>>

    init {
        val countryDao = AppDatabase.getDataBase(application).countryDao()
        countryRepository = CountryRepository(countryDao)
        countrySubscriptions = countryRepository.countrySubscriptions
    }

    /**
     * update countryList once the name in search changed
     */
    fun countryNameChanged(countryName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val v = countryRepository.getCountriesByName("$countryName%")
            countryList.postValue(v)
        }
    }

}