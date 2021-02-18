package com.example.covid19.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    val name: String,
    val slug: String,
    val iso2: String,
    val status: Int = 0
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
