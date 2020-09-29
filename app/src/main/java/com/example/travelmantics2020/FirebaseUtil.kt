package com.example.travelmantics2020

import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirebaseUtil{



    companion object {
        private const val RC_SIGN_IN: Int = 1
        var mFirebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        lateinit var mDatabaseReference: DatabaseReference
        lateinit var mFirebaseAuth: FirebaseAuth
        lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener
        private var firebaseUtil: FirebaseUtil? = null
        val mDeals: ArrayList<TravelDeal> = ArrayList()





}


    fun openFbReference(ref: String, callerActivity: ListFragment) {
        mDatabaseReference = mFirebaseDatabase.reference.child(ref)
        mFirebaseAuth = FirebaseAuth.getInstance()
        mAuthStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                signIn(callerActivity)
            }else{
                val userUid = it.currentUser!!.uid
                checkAdmin(userUid,callerActivity)
                Toast.makeText(callerActivity.requireActivity(), "Welcome Back", Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun checkAdmin(userUid: String?, callerActivity: ListFragment) {
        mFirebaseDatabase.reference.child("Administrators").child(userUid!!).addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                callerActivity.showMenu()
                PrefManager.getInstance(callerActivity.requireContext()).isAdmin = true
                Log.d("Admin", "onChildAdded: You are an Administrator ")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }

    private fun signIn(callerActivity: ListFragment) {
        // Choose authentication providers
        val providers = arrayListOf(
            IdpConfig.EmailBuilder().build(),
            IdpConfig.GoogleBuilder().build(),
        )

// Create and launch sign-in intent
        callerActivity.startActivityForResult(
            getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    fun attachListener() {
        mFirebaseAuth.addAuthStateListener(mAuthStateListener)
    }

    fun detachListener() {
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener)
    }

}

interface CheckAdministrator{
    var hasAdminRights : Boolean

    fun setRights(isAdmin : Boolean){
        hasAdminRights = isAdmin
    }

}