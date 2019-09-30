package farchan.app.com.belajardicoding;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import farchan.app.com.belajardicoding.View.Fragment.FavoriteFragment;
import farchan.app.com.belajardicoding.View.Fragment.MovieFragment;
import farchan.app.com.belajardicoding.View.Fragment.TVFragment;
import farchan.app.com.belajardicoding.View.Setting;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // set activity_main.xml sebagai layout untuk activty ini

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null){
            navigation.setSelectedItemId(R.id.movie);
        }

    }
    // meng init menu di toolbar android
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // ketika menu ditekan akan memberikan suatu aksi
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.change_language :
                Intent changeLang = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(changeLang);
                break;
            case R.id.setting:
                Intent setting = new Intent(MainActivity.this, Setting.class);
                startActivity(setting);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.movie:
                    toolbar.setTitle("Movie");
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.tv:
                    toolbar.setTitle("TV show");
                    fragment = new TVFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.favorite:
                    toolbar.setTitle("Favorite");
                    fragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }

    };

}