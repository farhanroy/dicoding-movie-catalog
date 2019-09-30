package farchan.app.com.belajardicoding.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import farchan.app.com.belajardicoding.Model.TvModel;
import farchan.app.com.belajardicoding.Repository.RetroTvRepo;

public class TvRetroViewModel extends ViewModel {
    private MutableLiveData<TvModel> mutableLiveData;
    private final RetroTvRepo tvRepo;

    public TvRetroViewModel() {
        tvRepo = new RetroTvRepo();
    }

    public void init() {
        if (this.mutableLiveData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        mutableLiveData = tvRepo.getTvs();
    }

    public MutableLiveData<TvModel> getTvs() {
        return this.mutableLiveData;
    }
}
