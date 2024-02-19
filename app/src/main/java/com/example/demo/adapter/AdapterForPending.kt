package com.example.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.databinding.ActivityHomeBinding

class AdapterForPending():RecyclerView.Adapter<AdapterForPending.viewHolder>() {
    class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imgPending = itemView.findViewById<ImageView>(R.id.img_tailor_image)
        val tvName = itemView.findViewById<TextView>(R.id.tv_pending_name)
        val tvMobile = itemView.findViewById<TextView>(R.id.tv_pending_mobile)
        val tvExpectedDate = itemView.findViewById<TextView>(R.id.tv_pending_expected_date)
        val tvDelay = itemView.findViewById<TextView>(R.id.tv_pending_delay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val pendingView = LayoutInflater.from(parent.context).inflate(R.layout.deshbord,parent,false)
        return viewHolder(pendingView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.tvName.text = "Krunal Lomeshbahi Soni"
        holder.tvMobile.text = "9104429451"
        holder.imgPending.setImageResource(R.drawable.hairslondemo)
        holder.tvExpectedDate.text = "3"
        holder.tvDelay.text = "0"
    }

    override fun getItemCount(): Int {
        return 10
    }
}