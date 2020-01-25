package com.jun.clover.lockscreen

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.os.SystemClock
import org.koin.android.ext.android.inject

class LockScreenService : Service() {
    private val lsReceiver : LockScreenReceiver by inject()
    private val rsReceiver : RestartReceiver by inject()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        registerReceiver(lsReceiver, filter)
        registerRestartAlarm(true)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(lsReceiver)
        registerRestartAlarm(false)
    }

    private fun registerRestartAlarm (isOn : Boolean) {
        val intent = Intent(this, RestartReceiver::class.java)
        intent.action = rsReceiver.ACTION_RESTART_SERVICE
        val sender = PendingIntent.getBroadcast(applicationContext, 0, intent, 0)

        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (isOn) {
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000, 60000, sender)
        }
        else {
            am.cancel(sender)
        }
    }
}