package com.example.covid19.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.covid19.entity.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Query("select * from Country where lower(name) like lower(:countryName)")
    fun findCountryByName(countryName:String): List<Country>

    @Insert
    fun insertAll ( countryArray: List<Country>)

    @Query("select * from Country where status = 1")
    fun findCountryByStatus():LiveData<List<Country>>

    @Query("select count(*) from Country")
    fun findAll():Int

    @Query("update Country set status = :status where name = :country  ")
    fun modifyCountryStatus(country:String, status:Int)

    @Query("select status from country where name = :country")
    fun getCountryStatusByName(country: String):Int
}