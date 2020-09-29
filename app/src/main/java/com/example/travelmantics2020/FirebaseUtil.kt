package com.example.travelmantics2020

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseUtil {

    companion object {
        private val RC_SIGN_IN: Int = 1
        var mFirebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        lateinit var mDatabaseReference: DatabaseReference
        lateinit var mFirebaseAuth: FirebaseAuth
        lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener
        private var firebaseUtil: FirebaseUtil? = null
        val mDeals: ArrayList<TravelDeal> = ArrayList()


        fun openFbReference(ref: String, callerActivity: FragmentActivity?) {
            mDatabaseReference = mFirebaseDatabase.reference.child(ref)
            mFirebaseAuth = FirebaseAuth.getInstance()
            mAuthStateListener = FirebaseAuth.AuthStateListener {
                if (it.currentUser == null) {
                    FirebaseUtil.signIn(callerActivity!!)
                }else{
                    Toast.makeText(callerActivity, "Welcome Back", Toast.LENGTH_LONG).show()
                }

            }
        }

        private fun signIn(callerActivity: FragmentActivity) {
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

}