package com.example.demo

import android.app.ProgressDialog
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


class VerifyNumber : AppCompatActivity() {
    private lateinit var btnVerifyAndContinue:Button
    private lateinit var etOtp:EditText

    private val REQ_USER_CONSENT = 200
    var smsbordcastRecever:SmsBordcastRecever? = null


    private lateinit var progressDialog: ProgressDialog

    lateinit var auth: FirebaseAuth

    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_number)


        supportActionBar?.hide()

        btnVerifyAndContinue = findViewById(R.id.btn_verify_continue)
        etOtp = findViewById(R.id.et_otp)
        //startSmartUserConsent()



        auth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        val storedVerificationId = intent.getStringExtra("storedVerificationId")
        val pref = this.getSharedPreferences("login_pref", MODE_PRIVATE)

        val phoneno = pref.getString("phoneno", "")

        btnVerifyAndContinue.setOnClickListener {
            /*val intent = Intent(this@VerifyNumber,SeceltProfile::class.java)
            startActivity(intent)*/
            val otp = findViewById<EditText>(R.id.et_otp).text.trim().toString()

            if (otp.isNotEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp
                )
                signInWithPhoneAuthCredential(credential)
                progressDialog.setMessage("Verifying Code...")
                progressDialog.show()

            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //registerBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
       // unregisterReceiver(smsbordcastRecever)
    }

    /*private fun startSmartUserConsent() {
        val client = SmsRetriever.getClient(this)
         client.startSmsUserConsent(null)

    }*/

    private fun startSmartUserConsent() {
        val client = SmsRetriever.getClient(this)
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener {
            Toast.makeText(
                applicationContext,
                "On Success",
                Toast.LENGTH_LONG
            ).show()
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "On OnFailure", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_USER_CONSENT){

            if (resultCode == RESULT_OK && data!= null){

                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                getOtpFromMessage(message)

            }

        }
    }

    private fun getOtpFromMessage(message: String?) {
        val otpPatter = Pattern.compile("(|^)\\d{6}")
        val matcher = otpPatter.matcher(message)
        if (matcher.find()){
            etOtp.setText(matcher.group(0))
        }
    }

/*    private fun registerBroadcastReceiver(){
        smsbordcastRecever = SmsBordcastRecever()
        smsbordcastRecever!!.smsBroascastReceiverListener = object :SmsBordcastRecever.SmsBroascastReceiverListener{
            override fun onSuccess(intent: Intent?) {
                startActivityForResult(intent,REQ_USER_CONSENT)
            }

            override fun onFailure() {

            }
        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsbordcastRecever,intentFilter)

    }*/

    private fun resendVerificationCode(
        mobile: String,
        resendingToken: PhoneAuthProvider.ForceResendingToken
    ) {
        progressDialog.setMessage("Resending Code...")
        progressDialog.show()

        val options = mCallBacks?.let {
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(mobile) // Phone number to verify
                .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(it) // OnVerificationStateChangedCallbacks
                .setForceResendingToken(resendToken)
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        progressDialog.setMessage("Verifying OTP.. ")

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    val pref = this.getSharedPreferences("login_pref", MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = pref.edit()
                    editor.putBoolean("is_logout", false)
                    editor.apply()

                    val intent = Intent(this, SeceltProfile::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                }
            }
    }
}