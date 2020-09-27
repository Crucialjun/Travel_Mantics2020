package com.example.travelmantics2020

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class DealAdapter() : RecyclerView.Adapter<DealAdapter.DealViewHolder>() {

    val dealsArray:ArrayList<TravelDeal> = ArrayList()
    val deals = FirebaseUtil.mDeals
    init {

        val firebaseUtil = FirebaseUtil.openFbReference("traveldeals")
        val mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase

        val mChildListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val deal = snapshot.getValue(TravelDeal::class.java)
                deal!!.id = snapshot.key
                deals.add(deal)
                notifyItemInserted(deals.size - 1)

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
                TODO("Not yet implemented")
            }

        }
        val mDatabaseReference = FirebaseUtil.mDatabaseReference.addChildEventListener(mChildListener)
    }







    class DealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)

        fun bind(deal : TravelDeal){
            tvTitle.text = deal.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val itemView : View  = LayoutInflater.from(parent.context).inflate(R.layout.rv_row,parent,false)
        return DealViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        val travelDeal = deals[position]
        holder.bind(travelDeal)
    }

    override fun getItemCount(): Int {
        return deals.size
    }
}