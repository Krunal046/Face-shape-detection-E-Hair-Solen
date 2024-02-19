package com.example.demo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.model.Barber
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Temp : AppCompatActivity() {

    private lateinit var etBarberName:EditText
    private lateinit var etBarberAddress:EditText
    private lateinit var etBarberNumber:EditText
    private lateinit var etBarberTiming:EditText
    private lateinit var etBarberWorkingDays:EditText
    private lateinit var btnBarberSubmit:Button

    //firebase
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)

        etBarberName = findViewById(R.id.et_barber_name)
        etBarberAddress = findViewById(R.id.et_barber_address)
        etBarberNumber = findViewById(R.id.et_barber_phone_number)
        etBarberTiming = findViewById(R.id.et_salon_time)
        etBarberWorkingDays = findViewById(R.id.et_working_days)
        btnBarberSubmit = findViewById(R.id.btn_barber_submit)



        btnBarberSubmit.setOnClickListener {

            database = FirebaseDatabase.getInstance("https://fir-e27d0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Shops")



            var name = etBarberName.text.toString()
            var address = etBarberAddress.text.toString()
            var number = etBarberNumber.text.toString()
            var time = etBarberTiming.text.toString()
            var workDay = etBarberWorkingDays.text.toString()

            val barber = Barber(name,address,number,time,workDay)

            database.child(name).setValue(barber).addOnSuccessListener {

                etBarberName.text.clear()
                etBarberAddress.text.clear()
                etBarberNumber.text.clear()
                etBarberTiming.text.clear()
                etBarberWorkingDays.text.clear()

                Toast.makeText(this, "Data Successfully Saved", Toast.LENGTH_SHORT).show()
                
            }.addOnFailureListener {

                Toast.makeText(this, "Faied", Toast.LENGTH_SHORT).show()

            }

        }

    }
}