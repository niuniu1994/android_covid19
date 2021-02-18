package com.example.covid19.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covid19.dao.CountryDao
import com.example.covid19.entity.Country
import kotlinx.coroutines.flow.Flow

/**
 * abstract level between ui and dao ,same as service in java
 */
class CountryRepository(private val countryDao: CountryDao) {

    val countrySubscriptions:LiveData<List<Country>> = countryDao.findCountryByStatus()

//    val countryNameList:LiveData<List<Country>> = countryDao.findCountryByName("")

    fun getAllCountrySubs(): LiveData<List<Country>> {
        return countryDao.findCountryByStatus()
    }

    fun addCountries( countries:List<Country>){
        countryDao.insertAll(countries)
    }

    fun getCountriesByName(countryName:String): List<Country> {
        return countryDao.findCountryByName(countryName)
    }

    fun getAll():Int{
        return countryDao.findAll()
    }

}