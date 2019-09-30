package farchan.app.com.belajardicoding.View.Fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import farchan.app.com.belajardicoding.Adapter.ListTVAdapter;
import farchan.app.com.belajardicoding.View.DetailTv;
import farchan.app.com.belajardicoding.Model.TvModel;
import farchan.app.com.belajardicoding.R;
import farchan.app.com.belajardicoding.Model.TV;
import farchan.app.com.belajardicoding.View.SearchTv;
import farchan.app.com.belajardicoding.ViewModel.TvRetroViewModel;


public class TVFragment extends Fragment {

    private ProgressBar progressBar;
    private ListTVAdapter adapter;
    private RecyclerView recyclerTv;
    private final List<TvModel.DataTv> tvList = new ArrayList<>();

    public TVFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
        View rootView = inflater.inflate(R.layout.fragment_tv, container, false);  // merubah viewgroup pada fragment_movie.xml menjadi layout
        recyclerTv = rootView.findViewById(R.id.recycler_tv); // init recyclerview widget
        progressBar = rootView.findViewById(R.id.progress_bar2);

        adapter = new ListTVAdapter(tvList);
        adapter.notifyDataSetChanged();

        TvRetroViewModel tvViewModel = ViewModelProviders.of(this).get(TvRetroViewModel.class);
        tvViewModel.init();
        tvViewModel.getTvs().observe(this, tvModel -> {
            tvList.addAll(Objects.requireNonNull(tvModel).getTvData());
            recyclerTv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Log.d("LK",tvModel.getTvData().get(0).toString());
            showLoading(false);
        });

        adapter.setOnItemClickListener(tv -> {
            TV tv1 = new TV();

            tv1.setTvName(tv.getTitleTv());
            tv1.setTvRelease(tv.getReleaseDateTv());
            tv1.setTvDescription(tv.getDescriptionTv());
            tv1.setPosterTV(tv.getImgTv());

            Intent moveTv = new Intent(getActivity(), DetailTv.class);
            moveTv.putExtra(DetailTv.EXTRA_TV, tv1);
            startActivity(moveTv);
        });
        recyclerTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerTv.setHasFixedSize(true);
        setHasOptionsMenu(true);
        showLoading(true);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }
    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Intent searchTv = new Intent(getActivity(), SearchTv.class);
                searchTv.putExtra(SearchTv.EXTRA_TV_SEARCH, s);
                startActivity(searchTv);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
