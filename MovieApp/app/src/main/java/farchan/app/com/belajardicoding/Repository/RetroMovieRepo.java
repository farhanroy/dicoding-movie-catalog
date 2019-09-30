package farchan.app.com.belajardicoding.Repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.res.Resources;
import android.support.v4.os.ConfigurationCompat;
import android.util.Log;

import java.util.Locale;

import farchan.app.com.belajardicoding.Api.Api;
import farchan.app.com.belajardicoding.BuildConfig;
import farchan.app.com.belajardicoding.Helper.ClientService;
import farchan.app.com.belajardicoding.Model.MovieModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroMovieRepo {


    private final Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
    private MutableLiveData<MovieModel> refferAndInvitePojoMutableLiveData;


    public RetroMovieRepo() {
    }

    public MutableLiveData<MovieModel> getMovies() {

        refferAndInvitePojoMutableLiveData = new MutableLiveData<>();
        Api api = ClientService.getApiService();


        Call<MovieModel> call = api.getJSON(BuildConfig.API_KEY, locale.getCountry().toLowerCase());
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.body()!=null)
                {
                    Log.d("wow", locale.getCountry().toLowerCase());
                    refferAndInvitePojoMutableLiveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Log.d("ERROR Retrofit", t.getMessage());
            }
        });

        return refferAndInvitePojoMutableLiveData;
    }

}
