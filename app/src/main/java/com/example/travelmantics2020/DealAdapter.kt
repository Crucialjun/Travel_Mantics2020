package com.example.travelmantics2020

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.fragment_insert.*

class DealAdapter() : RecyclerView.Adapter<DealAdapter.DealViewHolder>() {

    val dealsArray:ArrayList<TravelDeal> = ArrayList()
    val deals = FirebaseUtil.mDeals
    init {
        deals.clear()

        val mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase

        val mChildListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val deal = snapshot.getValue(TravelDeal::class.java)
                deal!!.id = snapshot.key
                deals.add(deal)
                notifyItemInserted(deals.size - 1)

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        val mDatabaseReference = FirebaseUtil.mDatabaseReference.addChildEventListener(mChildListener)
    }




    inner class DealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val imageView: ImageView = itemView.findViewById(R.id.imageDeal)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(deal : TravelDeal){
            tvTitle.text = deal.title
            tvDescription.text = deal.description
            tvPrice.text = deal.price
            showImage(deal.imgUrl!!)

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            Log.d("Deal Adapter", "onClick: position = $position")
            val selectedDeal = deals[position]
            val action = ListFragmentDirections.actionListFragmentToInsertFragment(selectedDeal)
            p0!!.findNavController().navigate(action)
        }

        fun showImage(url : String){
            Glide.with(imageView.context)
                .load(url)
                .apply(RequestOptions.overrideOf(220,220))
                .centerCrop()
                .into(imageView)
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