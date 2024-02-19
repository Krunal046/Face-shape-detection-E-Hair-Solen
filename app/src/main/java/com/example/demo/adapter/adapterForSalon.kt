package com.example.demo.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.HomeActivity
import com.example.demo.R
import com.example.demo.Shop
import com.example.demo.model.Barber
import com.google.firebase.database.DatabaseReference

class adapterForSalon(val context: Context , private val userList:ArrayList<Barber>) : RecyclerView.Adapter<adapterForSalon.ViewHolder>() {
    class ViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val ivSalon = viewItem.findViewById<ImageView>(R.id.iv_salon)
        val tvSalonName = viewItem.findViewById<TextView>(R.id.tv_salon_name)
        val tvSalonArea = viewItem.findViewById<TextView>(R.id.tv_salon_area)
        val cvSalon = viewItem.findViewById<CardView>(R.id.cd_salon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val salonView = LayoutInflater.from(parent.context).inflate(R.layout.salon, parent, false)
        return ViewHolder(salonView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivSalon.setImageResource(R.drawable.hairslondemo)
        holder.tvSalonArea.text = userList[position].address
        holder.tvSalonName.text = userList[position].name

        holder.cvSalon.setOnClickListener {
            var intentForShop = Intent(context, Shop::class.java)
            startActivity(context,intentForShop,null)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}