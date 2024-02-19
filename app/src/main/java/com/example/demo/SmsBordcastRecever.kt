package com.example.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsBordcastRecever:BroadcastReceiver() {
     var smsBroascastReceiverListener:SmsBroascastReceiverListener? = null


    override fun onReceive(p0: Context?, p1: Intent?) {

        if(SmsRetriever.SMS_RETRIEVED_ACTION == p1?.action){

            val extra = p1.extras
            val smsReceiverStatus = extra?.get(SmsRetriever.EXTRA_STATUS) as Status

            when(smsReceiverStatus.statusCode){

                CommonStatusCodes.SUCCESS ->{
                    val messageIntent = extra.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                    smsBroascastReceiverListener?.onSuccess(messageIntent)
                }

                CommonStatusCodes.TIMEOUT ->{
                    smsBroascastReceiverListener?.onFailure()
                }
            }

        }

    }

    interface SmsBroascastReceiverListener{

        fun onSuccess(intent: Intent?)

        fun onFailure()

    }

}