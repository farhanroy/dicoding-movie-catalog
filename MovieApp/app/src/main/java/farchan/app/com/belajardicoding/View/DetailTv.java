package farchan.app.com.belajardicoding.View;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.Objects;

import farchan.app.com.belajardicoding.Database.DatabaseContract;
import farchan.app.com.belajardicoding.Helper.FavoriteHelper;
import farchan.app.com.belajardicoding.MainActivity;
import farchan.app.com.belajardicoding.Model.Model;
import farchan.app.com.belajardicoding.Model.TV;
import farchan.app.com.belajardicoding.R;

import static farchan.app.com.belajardicoding.Database.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class DetailTv extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tv";
    private static final String TYPE = "tv";
    private FavoriteHelper favoriteHelper;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        Button btnFav = findViewById(R.id.btn_favorite_tv);
        TextView tvName = findViewById(R.id.tv_name);
        TextView tvDescription = findViewById(R.id.description_tv_detail);
        TextView tvRelease = findViewById(R.id.release_tv);
        ImageView imgPosterTv = findViewById(R.id.tv_poster_detail);

        TV tv = getIntent().getParcelableExtra(EXTRA_TV);

        tvName.setText(tv.getTvName());
        tvRelease.setText(tv.getTvRelease());
        Picasso.get().load(tv.getPosterTV()).into(imgPosterTv);

        if (tv.getTvDescription().isEmpty()){
            tvDescription.setText(R.string.lang_support);
        } else {
            tvDescription.setText(tv.getTvDescription());
        }

        Model model = new Model();
        model.setTitle(tv.getTvName());
        model.setRelease(tv.getTvRelease());
        model.setDescription(tv.getTvDescription());
        model.setPoster(tv.getPosterTV());
        model.setType(TYPE);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tv show detail");
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        favoriteHelper.open();

        values = new ContentValues();

        values.put(DatabaseContract.FavoriteColumns.COLUMN_NAME, tv.getTvName());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_RELEASE, tv.getTvRelease());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_DESCRIPTION, tv.getTvDescription());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_POSTER, tv.getPosterTV());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_TYPE, TYPE);


        if ( favoriteHelper.favoriteCheck(tv.getTvName()).moveToFirst()){
            btnFav.setText(R.string.btn_favorite_delete);
        }
        else {
            btnFav.setText(R.string.btn_favorite);
        }

        btnFav.setOnClickListener(v -> {
            try {
                if ( favoriteHelper.favoriteCheck(tv.getTvName()).moveToFirst()){
                    getContentResolver().delete(CONTENT_URI, tv.getTvName(), null);
                    recreate();
                    Toast.makeText(this, "Sudah Didelete", Toast.LENGTH_LONG).show();
                }
                else {
                    getContentResolver().insert(CONTENT_URI, values);
                    recreate();
                    Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
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
            Intent back = new Intent(DetailTv.this, MainActivity.class);
            startActivity(back);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteHelper.close();
    }
}
