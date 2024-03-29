package com.jun.clover.firebase

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jun.clover.R
import com.jun.clover.main.MainActivity
import com.jun.clover.repository.FirebaseTokenRepository
import org.koin.android.ext.android.inject

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val mFirebaseTokenRepository : FirebaseTokenRepository by inject()

    override fun onNewToken(token: String) {
        Log.e("new firebase token", "Refreshed token : $token, Length : ${token.length}")
        mFirebaseTokenRepository.addToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            Log.e("firebase message", "Notification Message : ${remoteMessage.notification?.body}")
            sendNotification(remoteMessage.notification?.body)
            Log.e("mvm trigger", "send a message")
            sendBroadcast(Intent("com.jun.clover.SEND_FIREBASE"))
        }
    }

    private fun sendNotification(body: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("Notification", body)
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, "Notification")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Push Notification FCM")
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(notificationSound)
            .setContentIntent(pendingIntent)

        val notificationManager : NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}