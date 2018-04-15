package com.example.administrator.news_controller.view;

import com.example.administrator.news_controller.DetailedNews;

public interface NewsView extends MvpView {
    void addDetailedNewsItem(DetailedNews detailedNews);
}
