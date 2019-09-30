package farchan.app.com.belajardicoding.Helper;

import farchan.app.com.belajardicoding.Api.Api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientService {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Api getApiService() {
        return getRetrofitInstance().create(Api.class);
    }
}
