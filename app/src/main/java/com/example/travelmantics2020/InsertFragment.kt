package com.example.travelmantics2020
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_insert.*
import kotlinx.android.synthetic.main.rv_row.*


class InsertFragment : androidx.fragment.app.Fragment() {


    private val mFirebaseDatabase = FirebaseDatabase.getInstance()
    private val mDatabaseReference = mFirebaseDatabase.reference.child("traveldeals")
    private val args : InsertFragmentArgs by navArgs()
    private var deal = TravelDeal()



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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_menu, menu)
        val isAdmin = PrefManager.getInstance(requireContext()).isAdmin
        if(isAdmin!!){
            menu.findItem(R.id.delete_menu).isVisible = true
            menu.findItem(R.id.save_menu).isVisible = true
            enableEditText(true)
        }else{
            menu.findItem(R.id.delete_menu).isVisible = false
            menu.findItem(R.id.save_menu).isVisible = false
            enableEditText(false)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu -> {
                saveDeal()
                Toast.makeText(context, "Deal Saved", Toast.LENGTH_LONG).show()
                clean()
                backtoList()
                return true
            }
            R.id.delete_menu ->{
                deleteDeal()
                Toast.makeText(context, "Deal Deleted", Toast.LENGTH_LONG).show()
                backtoList()
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
       deal.title = txtTitle.text.toString()
        deal.description = txtDescription.text.toString()
        deal.price = txtPrice.text.toString()

        if(deal.id == null){
            mDatabaseReference.push().setValue(deal)
        }else{
            mDatabaseReference.child(deal.id!!).setValue(deal)
        }

    }

    private fun deleteDeal(){
        if(deal == null){
            Toast.makeText(context, "Please save a deal before deleting", Toast.LENGTH_SHORT).show()
            return
        }

        mDatabaseReference.child(deal.id!!).removeValue()
    }

    private fun backtoList(){
        findNavController().navigate(R.id.action_insertFragment_to_listFragment)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(args.selectedDeal != null){
            deal = args.selectedDeal!!
        }
        txtTitle.setText(deal?.title)
        txtDescription.setText(deal?.description)
        txtPrice.setText(deal?.price)
    }

    fun enableEditText(isEnabled : Boolean){
        txtTitle.isEnabled = isEnabled
        txtDescription.isEnabled = isEnabled
        txtPrice.isEnabled = isEnabled
    }

}