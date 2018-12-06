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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.dicoding.lukman.catalogmovie.R;
import com.dicoding.lukman.catalogmovie.data.models.Result;
import com.dicoding.lukman.catalogmovie.ui.activiy.DetailMovie;
import java.util.Calendar;
import java.util.List;
import static com.dicoding.lukman.catalogmovie.data.utils.Constants.NOTIFICATION_ID;
import static com.dicoding.lukman.catalogmovie.data.utils.Constants.NOTIFICATON_CHANNEL_ID;
import static com.dicoding.lukman.catalogmovie.ui.activiy.DetailMovie.EXTRA_RESULT;

public class UpComingMovieReceiver extends BroadcastReceiver {
    private static final String TAG = "UpComingMovieReceiver";
    private static int notifId = 1234;
    private static final String ID;
    private static final String MOVIE_TITLE;
    private static final String MOVIE_ID;
    private static final String MOVIE_RELEASE_DATE;
    private static final String MOVIE_VOTE_COUNNT;
    private static final String MOVIE_POPULARITY;
    private static final String MOVIE_VOTE_AVERAGE;
    private static final String MOVIE_POSTER_PATH;
    private static final String MOVIE_OVERVIEW;

    static {
        ID = "movie_id";
        MOVIE_TITLE = "movie_title";
        MOVIE_ID = "movie_id";
        MOVIE_RELEASE_DATE = "MOVIE_RELEASE_DATE";
        MOVIE_VOTE_COUNNT = "MOVIE_VOTE_COUNNT";
        MOVIE_POPULARITY = "MOVIE_POPULARITY";
        MOVIE_VOTE_AVERAGE = "MOVIE_VOTE_AVERAGE";
        MOVIE_POSTER_PATH = "MOVIE_POSTER_PATH";
        MOVIE_OVERVIEW = "MOVIE_OVERVIEW";
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        int id = intent.getIntExtra(ID, 0);
        String title = intent.getStringExtra(MOVIE_TITLE);
        int movie_id = intent.getIntExtra(MOVIE_ID, 0);
        String releaseDate = intent.getStringExtra(MOVIE_RELEASE_DATE);
        int voteCount = intent.getIntExtra(MOVIE_VOTE_COUNNT, 0);
        Float popularity = intent.getFloatExtra(MOVIE_POPULARITY, 0);
        Float average = intent.getFloatExtra(MOVIE_VOTE_AVERAGE, 0);
        String path = intent.getStringExtra(MOVIE_POSTER_PATH);
        String overview = intent.getStringExtra(MOVIE_OVERVIEW);

        Result result = new Result(voteCount,
                movie_id,
                null,
                average,
                title,
                popularity,
                path,
                null,
                null,
                null,
                null,
                null,
                overview,
                releaseDate);

        Log.d(TAG, "onReceive Id : "+ id);
//        Log.d(TAG, "onReceive: Title : " +result.getTitle());
        String desc = "Hari ini "+ title +" release";
        sendNotification(context,  desc, id, title, result);

    }

    private void sendNotification(Context context,  String desc, int id, String title, Result result) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, DetailMovie.class);
        intent.putExtra(EXTRA_RESULT, result);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_on)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATON_CHANNEL_ID,
                    "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(NOTIFICATON_CHANNEL_ID);
            if (manager != null) {
                manager.createNotificationChannel(notificationChannel);
            }
        }
        if (manager != null) {
            manager.notify(id, builder.build());
        }
    }


    public void setReleaseAlarm(Context context, List<Result> results){
        int delay = 0;
        for (Result list : results){
            cancelAlarm(context);
            Log.d(TAG, "setReleaseAlarm: " +results.size());
//            Log.d(TAG, "setReleaseAlarm: Title " +list.getTitle() );
            AlarmManager alarmManager = (AlarmManager)  context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, UpComingMovieReceiver.class);
            intent.putExtra(MOVIE_TITLE, list.getTitle());
            intent.putExtra(ID, notifId);
            intent.putExtra(MOVIE_RELEASE_DATE, list.getReleaseDate());
            intent.putExtra(MOVIE_VOTE_COUNNT, list.getVoteCount());
            intent.putExtra(MOVIE_VOTE_AVERAGE, list.getVoteAverage());
            intent.putExtra(MOVIE_POPULARITY, list.getPopularity());
            intent.putExtra(MOVIE_POSTER_PATH, list.getPosterPath());
            intent.putExtra(MOVIE_OVERVIEW, list.getOverview());
//            intent.putExtra()
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            assert alarmManager != null;
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() + delay, pendingIntent);
            notifId += 1;
            delay += 3000;
        }

    }

    public void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getPendingIntent(context));
        }
    }

    private PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, UpComingMovieReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
