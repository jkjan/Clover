package com.jun.clover.lockscreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RestartReceiver : BroadcastReceiver() {
    val ACTION_RESTART_SERVICE = "RestartReceiver.restart"

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(ACTION_RESTART_SERVICE)) {
            val it = Intent(context, LockScreenService::class.java)
            context.startService(it)
        }
    }
}
