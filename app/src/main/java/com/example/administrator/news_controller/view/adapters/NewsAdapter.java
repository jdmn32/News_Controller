package com.example.administrator.news_controller.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.news_controller.News;
import com.example.administrator.news_controller.NewsItem;
import com.example.administrator.news_controller.R;
import com.example.administrator.news_controller.Utils;

import java.net.URL;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public static final String LOG_TAG = NewsAdapter.class.getName();

    private List<NewsItem> feedItems;
    private NewsSelectedListener listener;
    private Context context;

    public NewsAdapter(List<NewsItem> feedItems, Context context, NewsSelectedListener listener) {
        this.listener = listener;
        this.feedItems = feedItems;
        this.context = context;
    }

    public interface NewsSelectedListener {
        void onNewsSelected(String newsPath);
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public void clear() {
        feedItems.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<NewsItem> list) {
        feedItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Log.e(LOG_TAG, "onBindViewHolder()");
        NewsItem newsItem = feedItems.get(position);

        URL newsUrl = Utils.parseUrl(newsItem.getUrl());
        String newsPath = newsUrl.getPath();

        holder.title.setText(newsItem.getTitle());
        holder.newsPath = newsPath;
        holder.description.setText(newsItem.getDescription());
        holder.imageURL = newsItem.getImageUrl();
        if (newsItem.getImageUrl() != null) {
            Utils.setImageFromUrl(context, holder.image, newsItem.getImageUrl());
        } else {
            holder.image.setVisibility(View.GONE);
        }
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_holder, parent, false);
        return new NewsViewHolder(view);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        String newsPath;
        private ImageView image;
        private TextView title;
        private TextView description;
        String imageURL;

        public NewsViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                listener.onNewsSelected(newsPath);
                }
            });
        }
    }
}