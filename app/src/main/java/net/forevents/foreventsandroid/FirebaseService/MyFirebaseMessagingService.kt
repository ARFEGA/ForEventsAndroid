package net.forevents.foreventsandroid.FirebaseService

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.Util.showDialog

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
        //super.onNewToken(token)
        Log.d("TOKEN FIREBASE: ", token)

    }
    override  fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("MSG RECEIVED", "From: " + remoteMessage.getFrom())

        // Check if message contains a data payload.
        if (remoteMessage.getData().size > 0) {

            Log.d("MSG RECEIVED", "Message data payload: " + remoteMessage.getData())

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob()
                sendNotification(remoteMessage)
            } else {
                // Handle message within 10 seconds
                sendNotification(remoteMessage)
                //handleNow();
            }

        }

    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val title=remoteMessage.data["title"]
        val content=remoteMessage.data["content"]

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "EDMTKotlin"

        @RequiresApi(Build.VERSION_CODES.O)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,
                "EDMT Notification",
                NotificationManager.IMPORTANCE_MAX)

            //Config
            with(notificationChannel) {
                description = "EDMTDev Notification Channel"
                enableLights(true)
                lightColor = (Color.RED)
                vibrationPattern = longArrayOf(0, 1000, 500, 500)
                enableVibration(true)
            }

            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder (this,NOTIFICATION_CHANNEL_ID)

        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.notification_icon_background)
            .setContentTitle(title)
            .setContentText(content)

        //Key Server FireBase.
        /*
        AAAAaXfomec:APA91bFX8EaL2A8BkDrmztRbkcg_PFAMQY1CtObe0BiywhA_nikfwmMLltCO1ipHaciF9TgMVMTdZoQkjZffRWC-9_8H-lskMkykPVQr8GrUJrqp1aowe7_MIW_dydODRh12TlXEhq6X
         */
        notificationManager.notify(1,notificationBuilder.build())
    }

    private fun handleNow() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun scheduleJob() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
