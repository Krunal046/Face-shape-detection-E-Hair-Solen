package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    private lateinit var spLang:Spinner
    private lateinit var langList:ArrayList<String>
    private lateinit var btnNext:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        spLang = findViewById(R.id.sp_lang)
        btnNext = findViewById(R.id.btn_next)

        langList = ArrayList()

        langList.add("English")
        langList.add("Hindi")

        val spinnerAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, langList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spLang.adapter = spinnerAdapter
        spinnerAdapter.notifyDataSetChanged()

        btnNext.setOnClickListener {
            val intent = Intent(this@MainActivity,EnterMobileNumber::class.java)
            startActivity(intent)
        }

    }
}