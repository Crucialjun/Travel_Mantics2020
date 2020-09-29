package com.example.travelmantics2020

import android.content.Context
import androidx.core.content.edit

class PrefManager private constructor(context: Context){
    private val pref = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)

    var isAdmin : Boolean
    get() = pref.getBoolean("KEY_ADMIN",false)
    set(value) = pref.edit { putBoolean("KEY_ADMIN",value).apply() }

    fun clear(){
        pref.edit { clear() }
    }

    companion object : SingletonHolder<PrefManager,Context>(::PrefManager){
        private const val FILE_NAME = "Admin_Rights"
    }
}