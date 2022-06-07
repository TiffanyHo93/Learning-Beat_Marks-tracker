package com.example.learningbeat_spinner;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    //Override onReceive method
    @Override
    public void onReceive(Context context, Intent intent) {
        //Define notification manager for the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Define channel name variable
        String CHANNEL_ID="my_channel_01";
        //Define CharSequence variable
        CharSequence name = "my_channel";
        //Define description for the channel
        String desc = "This is my channel";
        //Define importance degree for the channel
        int importance = NotificationManager.IMPORTANCE_HIGH;

        //Create new notification channel
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        //Set the description for the channel
        channel.setDescription(desc);
        //Set the light for the notification
        channel.enableLights(true);
        //Set light color
        channel.setLightColor(Color.RED);
        //Enable vibration
        channel.enableVibration(true);
        //Set vibration style
        channel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
        //Invisible badge
        channel.setShowBadge(false);
        //Create notification channel
        notificationManager.createNotificationChannel(channel);
        //Create intent linked to MainActivity class
        Intent repeatIntent = new Intent(context, MainActivity.class);
        //Set flag for the intent
        repeatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Create pending intent for the notification
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeatIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        //Set all the needed variable for notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon((android.R.drawable.arrow_up_float))
                .setContentTitle("Good Morning")
                .setContentText("Let start a energetic day to achieve the goals!!!")
                .setAutoCancel(true);
        //Execute notification
        notificationManager.notify(100, builder.build());
    }
}
