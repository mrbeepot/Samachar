package io.github.mrbeepot.samachar.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.github.mrbeepot.samachar.newsapi.model.ApiResponse;
import io.github.mrbeepot.samachar.newsapi.model.Article;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCallbackAdapter implements Callback<ApiResponse> {

    private Context context;
    private RecyclerView recyclerView;

    public ApiCallbackAdapter(Context context, RecyclerView recyclerView) {

        this.context = context;
        this.recyclerView = recyclerView;
    }


    @Override
    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
        if(response.isSuccessful()) {
            NewsAdapter adapter = new NewsAdapter(response.body().getArticles(),this.context);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            this.recyclerView.setAdapter(adapter);
        } else {

        }
    }

    @Override
    public void onFailure(Call<ApiResponse> call, Throwable t) {

    }
}
