package farchan.app.com.belajardicoding.Repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.res.Resources;
import android.support.v4.os.ConfigurationCompat;
import android.util.Log;

import java.util.Locale;

import farchan.app.com.belajardicoding.Api.Api;
import farchan.app.com.belajardicoding.BuildConfig;
import farchan.app.com.belajardicoding.Helper.ClientService;
import farchan.app.com.belajardicoding.Model.TvModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroTvRepo {

    private final Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);

    public RetroTvRepo() {
    }

    public MutableLiveData<TvModel> getTvs() {
        final MutableLiveData<TvModel> refferAndInvitePojoMutableLiveData = new MutableLiveData<>();
        Api api = ClientService.getApiService();
        Call<TvModel> call = api.getJSONTv(BuildConfig.API_KEY, locale.getCountry().toLowerCase());
        call.enqueue(new Callback<TvModel>() {
            @Override
            public void onResponse(Call<TvModel> call, Response<TvModel> response) {
                if(response.body()!=null)
                {
                    refferAndInvitePojoMutableLiveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<TvModel> call, Throwable t) {

                Log.d("ERROR", t.getMessage());
            }
        });

        return refferAndInvitePojoMutableLiveData;
    }
}

