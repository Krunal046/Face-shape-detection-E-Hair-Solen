package com.example.demo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.dependencies.e


class Shop : AppCompatActivity() {
    private lateinit var btnBookAppointment: Button
    private lateinit var btnCall: ImageButton
    private lateinit var btnDirection: ImageButton
    private lateinit var btnShare: ImageButton
    private lateinit var btnHairStyle: ImageButton
    private lateinit var btnReview: ImageButton
    private lateinit var btnArTry:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        btnBookAppointment = findViewById(R.id.btn_shop_book_appointment)

        btnCall = findViewById(R.id.btn_shop_call)
        btnDirection = findViewById(R.id.img_shop_direction)
        btnHairStyle = findViewById(R.id.img_hair_style)
        btnShare = findViewById(R.id.img_share)
        btnReview = findViewById(R.id.img_shop_review)
        /*
        btnArTry = findViewById(R.id.btn_ar_try)*/

        btnBookAppointment.setOnClickListener {
            val intent = Intent(this@Shop,Booking::class.java)
            startActivity(intent)
        }

        btnHairStyle.setOnClickListener {
            val intent = Intent(this@Shop,Ar::class.java)
            startActivity(intent)
        }


        btnCall.setOnClickListener {
            val u = Uri.parse("tel:" + "9104429451")
            val i = Intent(Intent.ACTION_DIAL, u)
            startActivity(i)
        }

        btnDirection.setOnClickListener{
            val gmmIntentUri = Uri.parse("google.navigation:q=Natraj+Twonship,+Vadodara+Gujarat")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
        }

        btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        btnReview.setOnClickListener {
            val intent = Intent(this@Shop,Review::class.java)
            startActivity(intent)
        }


    }
}