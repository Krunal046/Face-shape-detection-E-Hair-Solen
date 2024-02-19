package com.example.demo.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.adapter.adapterForSalon
import com.example.demo.databinding.FragmentHomeBinding
import com.example.demo.model.Barber
import com.google.firebase.database.*

class HomeFragment : Fragment() {
    private lateinit var adapter:adapterForSalon
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var rvSalon:RecyclerView
    private lateinit var userList:ArrayList<Barber>

    //firebase
    private lateinit var database: DatabaseReference

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var con:Context = root.context

        userList = ArrayList()
        database = FirebaseDatabase.getInstance("https://fir-e27d0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Shops")

       /* database.child("kuldeep").child("number").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }*/

        rvSalon = root.findViewById(R.id.rv_salon)
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        adapter = adapterForSalon(con,userList)

        rvSalon.layoutManager = layoutManager


        getUserData()

        /*val textView: TextView = binding.textHome*/
        homeViewModel.text.observe(viewLifecycleOwner) {
           /* textView.text = it*/
        }
        return root
    }

    private fun getUserData() {

        database = FirebaseDatabase.getInstance("https://fir-e27d0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Shops")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){

                        val data = userSnapshot.getValue(Barber::class.java)
                        userList.add(data!!)

                    }

                    rvSalon.adapter = adapter
                    adapter.notifyDataSetChanged()

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}