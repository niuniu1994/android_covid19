package com.example.covid19

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.covid19.database.AppDatabase
import com.example.covid19.entity.Country
import com.example.covid19.services.NotificationService
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private var db:AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting up bottom nav bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.fragment)
        val appConfiguration = AppBarConfiguration(setOf(R.id.dashboard,R.id.dashboard))
        setupActionBarWithNavController(navController,appConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        db = AppDatabase.getDataBase(this)

        // Initialization database
        GlobalScope.launch {
            val num = db!!.countryDao().findAll()
            if (num == 0){
                initializationDataBase()
            }
        }

        Intent(this,NotificationService::class.java).also {
            startService(it)
        }


    }
    /**
     * Initialization country info database - add all country name from json to database
     */
     fun initializationDataBase(){

        var countryList = mutableListOf<Country>()
        val i: InputStream = this.assets.open("countries.json")
        val str = BufferedReader(InputStreamReader(i)).readText()
        val jsob = JSONObject(str)
        val jsonArray = jsob.getJSONArray("countries")
        for (index:Int in 0 until jsonArray.length()){
            val name = jsonArray.getJSONObject(index).getString("Country")
            val slug = jsonArray.getJSONObject(index).getString("Slug")
            val iso2 = jsonArray.getJSONObject(index).getString("ISO2")
            var country = Country(name,slug,iso2)
            countryList.add(country)
        }
        db!!.countryDao().insertAll(countryList)
    }

}