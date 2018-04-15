package com.example.administrator.news_controller.model;

import android.net.Uri;
import android.provider.BaseColumns;

public final class DbContract {

    public DbContract() {}

    public static final class CategoryEntries implements BaseColumns {
        public static final Uri CATEGORIES_URI = Uri.parse("sqlite://com.example.administrator.news_controller/categories");

        public static final String TABLE_NAME = "categories";

        public static final String CATEGORY_ID = "id";
        public static final String CATEGORY_NAME = "category_name";
    }

    public static final class NewsEntries implements BaseColumns {
        public static final Uri NEWS_URI = Uri.parse("sqlite://com.example.administrator.news_controller/news");

        public static final String TABLE_NAME = "news";

        public static final String NEWS_ID = "id";
        public static final String NEWS_TITLE = "news_title";
        public static final String NEWS_DATE = "news_date";
        public static final String NEWS_DESCRIPTION = "news_description";
        public static final String CATEGORY_ID = "category_id";
        public static final String FULL_DESCRIPTION = "full_description";
    }
}
