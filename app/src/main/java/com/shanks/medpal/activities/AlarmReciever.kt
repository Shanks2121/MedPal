package com.shanks.medpal.activities

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.shanks.medpal.R
import com.shanks.medpal.database.NumbersDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlarmReciever : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null) {
            smsSend(context)


            val longArray = longArrayOf(1000, 1000, 1000, 1000, 1000)
            val i = Intent(context, MainActivity::class.java)
            val fullScreenIntent = Intent(context, MainActivity::class.java)
            val fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                fullScreenIntent, PendingIntent.FLAG_IMMUTABLE)
            intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent =
                PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(context!!, "VibrateAlarm")
                .setSmallIcon(R.drawable.ic_notification)
                .setVibrate(longArray)
                .setContentTitle("Medicine Alert")
                .setContentText("It's Time For Your Medication")
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setPriority(NotificationCompat.DEFAULT_SOUND)


            val notificationManager = NotificationManagerCompat.from(context)

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            notificationManager.notify(123, builder.build())
        }
    }

    fun smsSend(context : Context){

        Log.d("smsSend", "SmsSend is called")
        val userInput = "7000114324, 7000054017, 9630434085"
        val pNoArray : List<String> = userInput.split(",")


        try {

            val smsManager: SmsManager
            if (Build.VERSION.SDK_INT >= 23) {

                smsManager = context.getSystemService(SmsManager::class.java)
            } else {

                smsManager = SmsManager.getDefault()
            }

            GlobalScope.launch {
                for(numbers in NumbersDatabase(context).numbersDao().getNumList()){
                    smsManager.sendTextMessage(numbers,null , "It's Time For Your Medication It's Your Reminder", null, null)
                }
            }


            Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {

            Toast.makeText(
                context,
                "Please enter all the data.." + e.message.toString(),
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

}