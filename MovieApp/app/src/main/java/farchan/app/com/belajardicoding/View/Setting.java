package farchan.app.com.belajardicoding.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;

import java.util.Objects;

import farchan.app.com.belajardicoding.MainActivity;
import farchan.app.com.belajardicoding.R;
import farchan.app.com.belajardicoding.Reminder.DailyReminder;
import farchan.app.com.belajardicoding.Reminder.TodayReleaseReminder;

public class Setting extends AppCompatActivity {

    private final String KEY_SWITCH = "Status_switch";
    private final String KEY_SWITCH1 = "today_release";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        DailyReminder  reminder = new DailyReminder();
        TodayReleaseReminder releaseReminder = new TodayReleaseReminder();
        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        Switch daily = findViewById(R.id.daily_reminder);
        Switch todayReminder = findViewById(R.id.today_reminder);
        preferences =  getApplicationContext().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        editor = preferences.edit();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle(R.string.setting);

        if(Objects.equals(preferences.getString(KEY_SWITCH, "missing"), "on")){
            daily.setChecked(true);
        } else if(Objects.equals(preferences.getString(KEY_SWITCH, "missing"), "off")){
            daily.setChecked(false);
        } else{
            Log.d("LO", "Missing");
        }

        if(Objects.equals(preferences.getString(KEY_SWITCH1, "missing"), "on")){
            todayReminder.setChecked(true);
        } else if(Objects.equals(preferences.getString(KEY_SWITCH1, "missing"), "off")){
            todayReminder.setChecked(false);
        } else{
            Log.d("LO", "Missing");
        }

        daily.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                setSwitch("on");
                Log.d("Prefence Status", preferences.getString(KEY_SWITCH, "missing"));
                reminder.startReminder(getBaseContext(), "07:00");
            }
            else{
                setSwitch("off");
                Log.d("Prefence Status", preferences.getString(KEY_SWITCH, "missing"));
                reminder.stopReminder(getBaseContext());
            }
        });

        todayReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                setSwitch1("on");
                Log.d("today reminder", preferences.getString(KEY_SWITCH1, "missing"));
                releaseReminder.startReminder(getBaseContext(), "07:01");
            }
            else{
                setSwitch1("off");
                Log.d("today reminder", preferences.getString(KEY_SWITCH1, "missing"));
                releaseReminder.stopReminder(getBaseContext());
            }
        });
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent back = new Intent(Setting.this, MainActivity.class);
            startActivity(back);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setSwitch(String status){
        editor.putString(KEY_SWITCH, status);
        editor.apply();
    }

    private void setSwitch1(String status){
        editor.putString(KEY_SWITCH1, status);
        editor.apply();
    }

}
