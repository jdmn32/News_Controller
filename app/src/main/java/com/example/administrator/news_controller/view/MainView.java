package com.example.administrator.news_controller.view;

import com.example.administrator.news_controller.News;

import java.util.List;

public interface MainView extends MvpView {
    void onNewsLoaded(List<News.NewsItem> news);
}
