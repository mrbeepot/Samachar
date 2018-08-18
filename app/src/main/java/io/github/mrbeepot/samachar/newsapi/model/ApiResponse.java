package io.github.mrbeepot.samachar.newsapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName("status") private String status;
    @SerializedName("totalResults") private Integer totalResults;
    @SerializedName("articles") private List<Article> articles;

    public ApiResponse(String status, Integer totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
