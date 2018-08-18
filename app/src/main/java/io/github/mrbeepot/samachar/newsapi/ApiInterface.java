package io.github.mrbeepot.samachar.newsapi;

import io.github.mrbeepot.samachar.newsapi.model.ApiResponse;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<ApiResponse> getTopHeadlines(
            @Query("apiKey") String apiKey,
            @Query("country") String country);
    @GET("top-headlines")
    Call<ApiResponse> getNewsByCategory(
            @Query("apiKey") String apiKey,
            @Query("country") String country,
            @Query("category") String category);
    @GET("top-headlines")
    Call<ApiResponse> getNewsByQuery(
            @Query("apiKey") String apiKey,
            @Query("country") String country,
            @Query("q") String query);

}
