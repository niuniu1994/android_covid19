package com.example.covid19.entity

data class Province(
    val province: String,
    val confirmed: Int = 0,
    val recovered: Int = 0,
    val deaths: Int = 0,
    val active: Int = 0
)
