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
import java.util.Locale;
import java.util.Objects;

import farchan.app.com.belajardicoding.Adapter.ListMovieAdapter;
import farchan.app.com.belajardicoding.Model.Movie;
import farchan.app.com.belajardicoding.Model.MovieModel;
import farchan.app.com.belajardicoding.R;
import farchan.app.com.belajardicoding.View.Detail;
import farchan.app.com.belajardicoding.View.SearchMovie;
import farchan.app.com.belajardicoding.ViewModel.MovieRetroViewModel;


public class MovieFragment extends Fragment {

    private ProgressBar progressBar;
    private ListMovieAdapter adapter;
    private RecyclerView recyclerMovie;
    private final List<MovieModel.Data> movieList = new ArrayList<>();

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);  // merubah viewgroup pada fragment_movie.xml menjadi layout
        recyclerMovie = rootView.findViewById(R.id.recycler_movie); // init recyclerview widget
        progressBar = rootView.findViewById(R.id.progress_bar);

        adapter = new ListMovieAdapter(movieList);
        adapter.setOnItemClickListener(movie -> {
            Movie movies = new Movie();
            movies.setMovieName(movie.getTitle());
            movies.setReleaseDate(movie.getReleaseDate());
            movies.setDescription(movie.getDescription());
            movies.setPosterMovie(movie.getPosterMovie());

            Intent moveMovie = new Intent(getActivity(), Detail.class);
            moveMovie.putExtra(Detail.EXTRA_NAME, movies);
            startActivity(moveMovie);
        });
        recyclerMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerMovie.setHasFixedSize(true);
        MovieRetroViewModel mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MovieRetroViewModel.class);
        mViewModel.init();

        mViewModel.getMovies().observe(this, movieModels -> {
            movieList.addAll(Objects.requireNonNull(movieModels).getData());
            recyclerMovie.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Log.d("LK",movieModels.getData().get(0).getDescription());
            showLoading(false);
        });
        showLoading(true);
        setHasOptionsMenu(true);
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

                    Intent searchMovie = new Intent(getActivity(), SearchMovie.class);
                    searchMovie.putExtra(SearchMovie.EXTRA_SEARCH_MOVIE, s);
                    startActivity(searchMovie);

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