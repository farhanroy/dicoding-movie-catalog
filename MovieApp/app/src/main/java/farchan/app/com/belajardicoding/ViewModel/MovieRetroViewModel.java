package farchan.app.com.belajardicoding.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import farchan.app.com.belajardicoding.Model.MovieModel;
import farchan.app.com.belajardicoding.Repository.RetroMovieRepo;

public class MovieRetroViewModel extends ViewModel {
    private MutableLiveData<MovieModel> mutableLiveData;
    private final RetroMovieRepo movieRepo;

    public MovieRetroViewModel() {
        movieRepo = new RetroMovieRepo();
    }

    public void init() {
        if (this.mutableLiveData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        mutableLiveData = movieRepo.getMovies();
    }

    public MutableLiveData<MovieModel> getMovies() {
        return this.mutableLiveData;
    }

}
