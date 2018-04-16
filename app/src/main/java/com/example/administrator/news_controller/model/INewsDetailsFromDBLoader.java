package com.example.administrator.news_controller.model;

public interface INewsDetailsFromDBLoader {
    void loadNewsDetailsFromDb(NewsDetailsFromDBLoader.DetailsFromDbListener listener);
}
