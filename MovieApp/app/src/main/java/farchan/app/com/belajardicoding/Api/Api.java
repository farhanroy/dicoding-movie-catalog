package farchan.app.com.belajardicoding.Api;

import farchan.app.com.belajardicoding.Model.MovieModel;
import farchan.app.com.belajardicoding.Model.TvModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("trending/movie/day")
    Call<MovieModel> getJSON( @Query("api_key") String apiKey,
                              @Query("language") String language);

    @GET("search/movie")
    Call<MovieModel> getMovieSearch( @Query("api_key") String apiKey,
                                     @Query("query") String query,
                                     @Query("language") String language);

    @GET("discover/movie")
    Call<MovieModel> getMovieReleaseToday( @Query("api_key") String apiKey,
                                     @Query("primary_release_date.gte") String gte,
                                     @Query("primary_release_date.lte") String lte);

    @GET("trending/tv/day")
    Call<TvModel> getJSONTv(@Query("api_key") String apiKey,
                            @Query("language") String language);

    @GET("search/tv")
    Call<TvModel> getTvSearch(@Query("api_key") String apiKey,
                                 @Query("query") String query,
                                 @Query("language") String language);
}