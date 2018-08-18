package io.github.mrbeepot.samachar.newsapi;

import android.content.Context;
import android.content.res.Resources;

import io.github.mrbeepot.samachar.R;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static String baseUrl = null;
    public static Retrofit getApiClient(Context context) {
        if(baseUrl == null) {
            try {
                baseUrl = context.getApplicationContext()
                        .getResources().getString(R.string.NEWS_API_BASE_URL);
            } catch (Resources.NotFoundException e) {
                return null;
            } catch (Exception e) {
                return null;
            }
        }
        if(retrofit == null) {
            try {
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                return retrofit;
            } catch (NullPointerException e) {
                return null;
            } catch (Exception e) {
                return null;
            }
        }
        return retrofit;
    }
}
