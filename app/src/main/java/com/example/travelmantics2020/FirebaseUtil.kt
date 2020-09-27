package com.example.travelmantics2020

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseUtil {

    companion object{
        var mFirebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
        lateinit var mDatabaseReference: DatabaseReference
        private var firebaseUtil: FirebaseUtil? = null
        val mDeals : ArrayList<TravelDeal> = ArrayList()




        fun openFbReference(ref:String){
            mDatabaseReference = mFirebaseDatabase.reference.child(ref)
        }
    }

}