package com.example.travelmantics2020

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_insert.*
import kotlinx.coroutines.supervisorScope


class InsertFragment : Fragment() {


    val mFirebaseDatabase = FirebaseDatabase.getInstance()
    val mDatabaseReference = mFirebaseDatabase.reference.child("traveldeals")


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu -> {
                saveDeal()
                Toast.makeText(context, "Deal Saved", Toast.LENGTH_LONG).show()
                clean()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clean() {
        txtTitle.text!!.clear()
        txtDescription.text!!.clear()
        txtPrice.text!!.clear()
        txtTitle.requestFocus()

    }

    private fun saveDeal() {
        val title = txtTitle.text.toString()
        val description = txtDescription.text.toString()
        val price = txtPrice.text.toString()

        val deal = TravelDeal(title, description, price, "")
        mDatabaseReference.push().setValue(deal)
    }


}