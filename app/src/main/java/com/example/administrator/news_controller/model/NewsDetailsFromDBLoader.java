package com.example.administrator.news_controller.model;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.example.administrator.news_controller.DetailedNews;

import java.lang.ref.WeakReference;

public class NewsDetailsFromDBLoader implements INewsDetailsFromDBLoader {

    private Context context;

    public static final String LOG_TAG = NewsDetailsFromDBLoader.class.getName();

    public NewsDetailsFromDBLoader(Context context) {
        this.context = context;
    }

    public interface DetailsFromDbListener{
        void onLoaded(DetailedNews detailedNews);
    }

    public void loadNewsDetailsFromDb(String newsPath, DetailsFromDbListener listener){
        DbLoader dbLoader = new DbLoader(context, newsPath, listener);
        Thread thread = new Thread(dbLoader);
        thread.start();
    }

    static class DbLoader implements Runnable {

        Handler handler = new Handler();
        private Cursor cursor;
        Context context;
        String newsPath;
        private final WeakReference<DetailsFromDbListener> listener;

        public DbLoader(Context context, String newsPath, DetailsFromDbListener listener) {
            this.context = context;
            this.newsPath = newsPath;
            this.listener = new WeakReference(listener);
        }

        @Override
        public void run() {
            final DetailsFromDbListener detailsListener = listener.get();
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery("SELECT * FROM news WHERE id = " + newsPath, null);
            if (cursor != null) {


                cursor.moveToFirst();
                Integer id = cursor.getInt(cursor.getColumnIndex(DbContract.NewsEntries.NEWS_ID));
                String title = cursor.getString(cursor.getColumnIndex(DbContract.NewsEntries.NEWS_TITLE));
                String date = cursor.getString(cursor.getColumnIndex(DbContract.NewsEntries.NEWS_DATE));
                String shortDescription = cursor.getString(cursor.getColumnIndex(DbContract.NewsEntries.NEWS_DESCRIPTION));
                String fullDescription = cursor.getString(cursor.getColumnIndex(DbContract.NewsEntries.FULL_DESCRIPTION));
//                final DetailedNews detailedNews = new DetailedNews(id, title, date, shortDescription, fullDescription);

                if (detailsListener != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
//                            detailsListener.onLoaded(detailedNews);
                        }
                    });
                }
            }
        }
    }

}
