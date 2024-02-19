package com.example.demo

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class EnterMobileNumber : AppCompatActivity() {
    private lateinit var btnClose:Button
    private lateinit var btnContinue:Button
    private lateinit var etMobileNumber: EditText
    private lateinit var MobileNumber:String

    private lateinit var fireBase:FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mCallBacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var progressDialog: ProgressDialog
    var number : String =""
    val Req_Code: Int = 123

    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_mobile_number)

        supportActionBar?.hide()

        FirebaseApp.initializeApp(this)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        btnContinue = findViewById(R.id.btn_continue)
        etMobileNumber = findViewById(R.id.et_mobile_number)

        MobileNumber = etMobileNumber.text.toString()

        btnContinue.setOnClickListener {

            /*val intent = Intent(this@EnterMobileNumber,VerifyNumber::class.java)
            startActivity(intent)*/
            login()
        }

        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                progressDialog.dismiss()
                startActivity(Intent(applicationContext, MobileNumber::class.java))
                finish()
                Log.d("GFG" , "onVerificationCompleted Success")
            }

            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                //progressDialog.dismiss()
                Log.d("GFG" , "onVerificationFailed  $e")
            }

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                Log.d("GFG","onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token
                progressDialog.dismiss()

                // Start a new activity using intent
                // also send the storedVerificationId using intent
                // we will use this id to send the otp back to firebase
                val intent = Intent(applicationContext,VerifyNumber::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun login() {
        number = findViewById<EditText>(R.id.et_mobile_number).text.trim().toString()

        // get the phone number from edit text and append the country cde with it
        if (number.isNotEmpty()){
            number = "+91$number"
            sendVerificationCode(number)
        }else{
            Toast.makeText(this,"Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationCode(number: String) {

        progressDialog.setMessage("Verifying Phone Number...")
        progressDialog.show()

        val options = /*mCallBacks?.let*/
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(mCallBacks) // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        /*if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }*/
    }

    }
