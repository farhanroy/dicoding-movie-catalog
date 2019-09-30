package farchan.app.com.belajardicoding.View;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import farchan.app.com.belajardicoding.Adapter.ListMovieAdapter;
import farchan.app.com.belajardicoding.Api.Api;
import farchan.app.com.belajardicoding.BuildConfig;
import farchan.app.com.belajardicoding.Helper.ClientService;
import farchan.app.com.belajardicoding.MainActivity;
import farchan.app.com.belajardicoding.Model.Movie;
import farchan.app.com.belajardicoding.Model.MovieModel;
import farchan.app.com.belajardicoding.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMovie extends AppCompatActivity {
    public static final String EXTRA_SEARCH_MOVIE = "extra_search_movie";
    private RecyclerView recyclerView;
    private ListMovieAdapter adapter;
    private final Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
    private final List<MovieModel.Data> movieSearch = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);


        String keyword = getIntent().getStringExtra(EXTRA_SEARCH_MOVIE);

        progressBar = findViewById(R.id.progress_bar_movie);
        recyclerView = findViewById(R.id.recycler_movie_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ListMovieAdapter(movieSearch);
        adapter.setOnItemClickListener(movie -> {
            Movie movies = new Movie();
            movies.setMovieName(movie.getTitle());
            movies.setReleaseDate(movie.getReleaseDate());
            movies.setDescription(movie.getDescription());
            movies.setPosterMovie(movie.getPosterMovie());

            Intent moveMovie = new Intent(SearchMovie.this, Detail.class);
            moveMovie.putExtra(Detail.EXTRA_NAME, movies);
            startActivity(moveMovie);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        showLoading(true);

        getSearchMovies(keyword);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Objects.requireNonNull(getSupportActionBar()).setTitle(keyword);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent back = new Intent(SearchMovie.this, MainActivity.class);
            startActivity(back);
        }

        return super.onOptionsItemSelected(item);
    }

    private void getSearchMovies(String keyword) {

        Api api = ClientService.getApiService();


        Call<MovieModel> call = api.getMovieSearch(BuildConfig.API_KEY, keyword, locale.getCountry().toLowerCase());
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.body()!=null)
                {
                    movieSearch.addAll(response.body().getData());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.d("tag", response.body().getData().get(0).getTitle());
                    showLoading(false);
                }

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Log.d("ERROR Retrofit", t.getMessage());
            }
        });
    }

    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
