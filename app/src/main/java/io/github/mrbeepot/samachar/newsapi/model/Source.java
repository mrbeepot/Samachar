package io.github.mrbeepot.samachar.newsapi.model;

import com.google.gson.annotations.SerializedName;

public class Source {
    @SerializedName("id") private String id;
    @SerializedName("name") private String name;

    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
