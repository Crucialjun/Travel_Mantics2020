package com.example.travelmantics2020

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    companion object{

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_activity_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

        val insertMenu = menu.findItem(R.id.insert_menu)
        insertMenu.isVisible = FirebaseUtil.isAdmin

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.insert_menu -> {
                findNavController().navigate(R.id.action_listFragment_to_insertFragment)
                return true
            }
            R.id.logout_menu -> {
                AuthUI.getInstance().signOut(requireContext()).addOnCompleteListener {
                    Log.d(
                        "LOGOUT",
                        "onComplete: User Logged Out"
                    )
                    FirebaseUtil.attachListener()
                }
                FirebaseUtil.detachListener()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        FirebaseUtil.detachListener()
    }

    override fun onResume() {
        super.onResume()
        FirebaseUtil.openFbReference("traveldeals",this)

        val dealAdapater = DealAdapter()
        rvDeals.adapter = dealAdapater
        val dealslayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rvDeals.layoutManager = dealslayoutManager
        FirebaseUtil.attachListener()
    }

    fun showMenu(){
        requireActivity().invalidateOptionsMenu()
    }
}