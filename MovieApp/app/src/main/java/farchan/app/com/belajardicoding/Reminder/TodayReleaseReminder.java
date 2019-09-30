package farchan.app.com.belajardicoding.Reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import farchan.app.com.belajardicoding.Api.Api;
import farchan.app.com.belajardicoding.BuildConfig;
import farchan.app.com.belajardicoding.Helper.ClientService;
import farchan.app.com.belajardicoding.Model.Movie;
import farchan.app.com.belajardicoding.Model.MovieModel;
import farchan.app.com.belajardicoding.R;
import farchan.app.com.belajardicoding.View.Detail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayReleaseReminder extends BroadcastReceiver {
    private static final int REQUEST_CODE_RELEASE = 31;
    @Override
    public void onReceive(Context context, Intent intent) {
        getReleaseToday(context);
        Log.d("Reminder", "Release Today");
    }

    private void getReleaseToday(final Context context){
        Date cal = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String dateToday = dateFormat.format(cal);

        Api api = ClientService.getApiService();


        Call<MovieModel> call = api.getMovieReleaseToday(BuildConfig.API_KEY, dateToday, dateToday);
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.isSuccessful()){
                    if(response.body()!=null) {
                        Handler handler = new Handler();
                        Runnable r = () -> showAlarmNotification(context, response.body().getData().get(0));
                        handler.post(r);
                        Log.d("as", response.body().getData().get(0).getTitle());
                    }
                }

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Log.d("ERROR Retrofit", t.getMessage());
            }
        });
    }

    private void showAlarmNotification(Context context, MovieModel.Data data) {
        final Bitmap[] largeIcon = new Bitmap[1];
        Picasso.get()
                .load(data.getPosterMovie())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                        largeIcon[0] = bitmap;
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "AlarmManager_channel";
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Intent intent;
        PendingIntent pendingIntent;

        Movie movies = new Movie();
        movies.setMovieName(data.getTitle());
        movies.setReleaseDate(data.getReleaseDate());
        movies.setDescription(data.getDescription());
        movies.setPosterMovie(data.getPosterMovie());

        Bundle bundle = new Bundle();
        bundle.putParcelable(Detail.EXTRA_NAME, movies);

            intent = new Intent(context, Detail.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            pendingIntent = TaskStackBuilder.create(context)
                    .addNextIntent(intent)
                    .getPendingIntent(REQUEST_CODE_RELEASE, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle(context.getString(R.string.release_today_reminder))
                    .setContentText(data.getTitle())
                    .setSmallIcon(R.drawable.ic_notifications_48dp)
                    .setLargeIcon(largeIcon[0])
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound)
                    .setAutoCancel(true);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.notify(0, builder.build());
            }

        }


    public void startReminder(Context context, String time) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TodayReleaseReminder.class);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_RELEASE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void stopReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_RELEASE, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
