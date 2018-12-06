package com.dicoding.lukman.catalogmovie.data.notification.receiver;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.dicoding.lukman.catalogmovie.R;

import java.util.Calendar;

import static com.dicoding.lukman.catalogmovie.data.utils.Constants.NOTIFICATION_ID;
import static com.dicoding.lukman.catalogmovie.data.utils.Constants.NOTIFICATION_NAME;
import static com.dicoding.lukman.catalogmovie.data.utils.Constants.NOTIFICATON_CHANNEL_ID;


public class MovieDailyReceiver extends BroadcastReceiver {
    private static final String TAG = "MovieDailyReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: test alarm");

        sendNotification(context, context.getString(R.string.app_name),
                context.getString(R.string.message_daily), NOTIFICATION_ID);

    }
    private void sendNotification(Context context, String title, String message, int notifId) {
        Log.d(TAG, "sendNotification: test notification");

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_on)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setChannelId(NOTIFICATON_CHANNEL_ID)
                .setSound(alarmSound);

        assert notificationManagerCompat != null;
        notificationManagerCompat.notify(notifId, builder.build());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATON_CHANNEL_ID,
                    NOTIFICATION_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }
        notificationManagerCompat.notify(notifId, builder.build());

    }

    public void setAlarm(Context context) {
        Log.d(TAG, "setAlarm: test");
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Log.d(TAG, "Calendar: " + calendar.getTimeInMillis());
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    getPendingIntent(context));
        }
    }

    public void cancelAlarm(Context context) {
        Log.d(TAG, "cancelAlarm: test");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getPendingIntent(context));
        }
    }
    private static PendingIntent getPendingIntent(Context context) {
        Log.d(TAG, "getPendingIntent: test alarm");
        Intent intent = new Intent(context, MovieDailyReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent,
                0);
    }

}
