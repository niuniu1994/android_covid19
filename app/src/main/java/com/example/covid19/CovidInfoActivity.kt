package com.example.covid19

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CovidInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covidinfo_activity)
        val country = intent.getStringExtra("country")
    }
}