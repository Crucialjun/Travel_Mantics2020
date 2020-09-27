package com.example.travelmantics2020

import java.io.Serializable

data class TravelDeal(
    var id : String?,
    val title: String,
    val description: String,
    val price: String,
    val imgUrl: String) : Serializable {

    constructor() : this(null,"","","","")

}