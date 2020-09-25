package com.example.travelmantics2020

import java.io.Serializable

data class TravelDeal(
    val title: String,
    val description: String,
    val price: String,
    val imgUrl: String) : Serializable {

}