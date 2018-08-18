package io.github.mrbeepot.samachar.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.mrbeepot.samachar.R;
import io.github.mrbeepot.samachar.adapters.ApiCallbackAdapter;
import io.github.mrbeepot.samachar.adapters.NewsAdapter;
import io.github.mrbeepot.samachar.newsapi.ApiInterface;
import io.github.mrbeepot.samachar.newsapi.model.ApiResponse;
import io.github.mrbeepot.samachar.newsapi.model.Article;
import retrofit2.Call;

public class NewsCategoryFragment extends Fragment {

    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    private String category;
    private List<Article> articles;
    private ApiInterface apiInterface;
    private String apiKey;
    public NewsCategoryFragment() {

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView)inflater.inflate(R.layout.fragment_news_category,container,false);
        return recyclerView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadNews();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void setParameters(String category, String apiKey, ApiInterface apiInterface) {
        this.category = category;
        this.apiKey = apiKey;
        this.apiInterface = apiInterface;
    }

    public void loadNews() {
        if(parametersAreSet()) {
            Call<ApiResponse> call;
            if(category.equals("top")) {
                call = this.apiInterface.getTopHeadlines(apiKey,"in");
            } else {
                call = this.apiInterface.getNewsByCategory(apiKey,"in",category);
            }
            ApiCallbackAdapter apiCallbackAdapter = new ApiCallbackAdapter(getContext(),recyclerView);
            call.enqueue(apiCallbackAdapter);
        } else {

        }


    }

    private boolean parametersAreSet() {
        if(this.category == null || this.apiInterface == null || this.apiKey == null) {
            return false;
        } else return true;
    }
}
