package farchan.app.com.belajardicoding.View;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import farchan.app.com.belajardicoding.Database.DatabaseContract;
import farchan.app.com.belajardicoding.Helper.FavoriteHelper;
import farchan.app.com.belajardicoding.MainActivity;
import farchan.app.com.belajardicoding.Model.Model;
import farchan.app.com.belajardicoding.Model.Movie;
import farchan.app.com.belajardicoding.R;
import farchan.app.com.belajardicoding.widget.FavoriteWidget;

import static farchan.app.com.belajardicoding.Database.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class Detail extends AppCompatActivity{
    public static final String EXTRA_NAME = "extra_name";
    private static final String TYPE = "movie";
    private Movie movie;
    private FavoriteHelper favoriteHelper;
    private ContentValues values;

    @SuppressLint({"StaticFieldLeak", "CheckResult"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movie = getIntent().getParcelableExtra(EXTRA_NAME);
        Toolbar toolbar = findViewById(R.id.toolbar);
        final Button btnFav = findViewById(R.id.btn_favorite);
        TextView txtName = findViewById(R.id.txt_movie_name);
        TextView txtAir = findViewById(R.id.txt_first_air);
        TextView txtDescription = findViewById(R.id.description_movie);
        ImageView imgPosterMovie = findViewById(R.id.movie_poster);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());

        Model model = new Model();
        model.setTitle(movie.getMovieName());
        model.setRelease(movie.getReleaseDate());
        model.setDescription(movie.getDescription());
        model.setPoster(movie.getPosterMovie());
        model.setType(TYPE);



        txtName.setText(movie.getMovieName());
        txtAir.setText(movie.getReleaseDate());
        Picasso.get().load(movie.getPosterMovie()).into(imgPosterMovie);

        if(movie.getDescription().isEmpty()){
            txtDescription.setText(R.string.lang_support);
        }else{
            txtDescription.setText(movie.getDescription());
        }

        setSupportActionBar(toolbar);
        favoriteHelper.open();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Objects.requireNonNull(getSupportActionBar()).setTitle("Movie detail");
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        values = new ContentValues();

        values.put(DatabaseContract.FavoriteColumns.COLUMN_NAME, movie.getMovieName());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_RELEASE, movie.getReleaseDate());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_DESCRIPTION, movie.getDescription());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_POSTER, movie.getPosterMovie());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_TYPE, TYPE);

        if(favoriteHelper.favoriteCheck(movie.getMovieName()).moveToFirst()){
            btnFav.setText(R.string.btn_favorite_delete);
        }else{
            btnFav.setText(R.string.btn_favorite);
        }
        btnFav.setOnClickListener(v -> {
            try {
                if ( favoriteHelper.favoriteCheck(movie.getMovieName()).moveToFirst()){
                    getContentResolver().delete(CONTENT_URI, movie.getMovieName(), null);
                    recreate();
                    Toast.makeText(this, "Sudah Didelete", Toast.LENGTH_LONG).show();
                    updateWidget();
                }
                else {
                    getContentResolver().insert(CONTENT_URI, values);
                    recreate();
                    Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
                    updateWidget();
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.d("Ada Error", e.getMessage());
                Toast.makeText(this, "Ada Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent back = new Intent(Detail.this, MainActivity.class);
            startActivity(back);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteHelper.close();
    }

    private void updateWidget(){
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        ComponentName theWidget = new ComponentName(this, FavoriteWidget.class);
        int[] appWidgetIds = manager.getAppWidgetIds(theWidget);
        Handler handler = new Handler();
        Runnable r  = () -> manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
        handler.post(r);

    }
}
