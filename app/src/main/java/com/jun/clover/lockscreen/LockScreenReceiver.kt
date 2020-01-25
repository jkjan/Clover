package com.jun.clover.lockscreen

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager

class LockScreenReceiver : BroadcastReceiver(){
    private var tM : TelephonyManager? = null
    private var isPhoneIdle = true

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_SCREEN_ON)) {

            if (tM == null) {
                tM = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                tM?.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE)
            }

            if (isPhoneIdle) {
                val it = Intent(context, LockScreenActivity::class.java)
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(it)
            }
        }
    }

    @SuppressLint("NewApi")
    private val phoneListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state : Int, incomingNumber : String) {
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> isPhoneIdle = true
                TelephonyManager.CALL_STATE_RINGING -> isPhoneIdle = false
                TelephonyManager.CALL_STATE_OFFHOOK -> isPhoneIdle = false
            }
        }
    }
}