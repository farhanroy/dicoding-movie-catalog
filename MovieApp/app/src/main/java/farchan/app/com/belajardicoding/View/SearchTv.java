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
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import farchan.app.com.belajardicoding.Adapter.ListTVAdapter;
import farchan.app.com.belajardicoding.Api.Api;
import farchan.app.com.belajardicoding.BuildConfig;
import farchan.app.com.belajardicoding.Helper.ClientService;
import farchan.app.com.belajardicoding.Model.TV;
import farchan.app.com.belajardicoding.Model.TvModel;
import farchan.app.com.belajardicoding.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTv extends AppCompatActivity {
    public static final String EXTRA_TV_SEARCH  = "extra_tv_search";
    private RecyclerView rvSearchTv;
    private ProgressBar progressBar;
    private ListTVAdapter adapter;
    private final Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
    private final List<TvModel.DataTv> tvSearch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv);

        String keyword = getIntent().getStringExtra(EXTRA_TV_SEARCH);

        Toolbar toolbar = findViewById(R.id.toolbar);
        rvSearchTv = findViewById(R.id.recycler_tv_search);
        progressBar = findViewById(R.id.progress_bar_tv);

        adapter = new ListTVAdapter(tvSearch);
        showLoading(true);
        getSearchTv(keyword);
        setSupportActionBar(toolbar);

        adapter.setOnItemClickListener(tv -> {
            TV tv1 = new TV();

            tv1.setTvName(tv.getTitleTv());
            tv1.setTvRelease(tv.getReleaseDateTv());
            tv1.setTvDescription(tv.getDescriptionTv());
            tv1.setPosterTV(tv.getImgTv());

            Intent moveTv = new Intent(this, DetailTv.class);
            moveTv.putExtra(DetailTv.EXTRA_TV, tv1);
            startActivity(moveTv);
        });
        rvSearchTv.setLayoutManager(new LinearLayoutManager(this));
        rvSearchTv.setHasFixedSize(true);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Objects.requireNonNull(getSupportActionBar()).setTitle(keyword);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

    }

    private void getSearchTv(String keyword) {

        Api api = ClientService.getApiService();


        Call<TvModel> call = api.getTvSearch(BuildConfig.API_KEY, keyword, locale.getCountry().toLowerCase());
        call.enqueue(new Callback<TvModel>() {
            @Override
            public void onResponse(Call<TvModel> call, Response<TvModel> response) {
                if(response.body()!=null)
                {
                    tvSearch.addAll(response.body().getTvData());
                    rvSearchTv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.d("tag", response.body().getTvData().get(0).getTitleTv());
                    showLoading(false);
                }

            }

            @Override
            public void onFailure(Call<TvModel> call, Throwable t) {
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
