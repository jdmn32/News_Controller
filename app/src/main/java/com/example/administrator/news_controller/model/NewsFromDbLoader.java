package com.example.administrator.news_controller.model;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.example.administrator.news_controller.News;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NewsFromDbLoader implements INewsFromDbLoader {

    private Context context;

    public static final String LOG_TAG = NewsFromDbLoader.class.getName();

    public NewsFromDbLoader(Context context) {
        this.context = context;
    }

    public interface NewsListener{
        void onLoaded(List<News.NewsItem> news);
    }

    public void loadNewsFromDb(NewsListener listener){
        DbLoader dbLoader = new DbLoader(context, listener);
        Thread thread = new Thread(dbLoader);
        thread.start();
    }

    static class DbLoader implements Runnable {

        Handler handler = new Handler();
        private Cursor cursor;
        Context context;
        private Integer id;
        private final WeakReference<NewsListener> listener;

        public DbLoader(Context context, NewsListener listener) {
            this.context = context;
            this.id = id;
            this.listener = new WeakReference(listener);
        }

        @Override
        public void run() {
            final NewsListener newsListener = listener.get();
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery("SELECT * FROM news WHERE category_id = " + id, null);
            if (cursor != null) {
                final ArrayList<News.NewsItem> news = new ArrayList<>();
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    Integer id = cursor.getInt(cursor.getColumnIndex(DbContract.NewsEntries.NEWS_ID));
                    String title = cursor.getString(cursor.getColumnIndex(DbContract.NewsEntries.NEWS_TITLE));
                    String date = cursor.getString(cursor.getColumnIndex(DbContract.NewsEntries.NEWS_DATE));
                    String shortDescription = cursor.getString(cursor.getColumnIndex(DbContract.NewsEntries.NEWS_DESCRIPTION));
//                    News.NewsItem newsItem = new News.NewsItem(title, date, shortDescription);
//                    news.add(newsItem);
                }

                if (newsListener != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            newsListener.onLoaded(news);
                        }
                    });
                }
            }
        }
    }
}
