package io.github.mrbeepot.samachar.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.mrbeepot.samachar.R;
import io.github.mrbeepot.samachar.newsapi.model.Article;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    List<Article> articles;
    private Context context;
    public NewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articles.get(position);
        String t = article.getTitle();
        holder.titleTextView.setText(article.getTitle());
        Glide.with(context)
                .load(article.getUrlToImage())
                .apply(new RequestOptions().placeholder(R.drawable.rubber_duck))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView image;

        public ViewHolder(View v) {
            super(v);
            titleTextView = (TextView)v.findViewById(R.id.row_news_title);
            image = (ImageView)v.findViewById(R.id.row_news_image);
        }
    }
}
