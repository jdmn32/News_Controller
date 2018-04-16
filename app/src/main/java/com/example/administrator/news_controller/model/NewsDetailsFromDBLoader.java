package com.example.administrator.news_controller.model;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.example.administrator.news_controller.DetailedNews;
import com.example.administrator.news_controller.News;

import java.lang.ref.WeakReference;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class NewsDetailsFromDBLoader implements INewsDetailsFromDBLoader {

    private Context context;

    public static final String LOG_TAG = NewsDetailsFromDBLoader.class.getName();

    public NewsDetailsFromDBLoader(Context context) {
        this.context = context;
    }

    public interface DetailsFromDbListener{
        void onLoaded(DetailedNews detailedNews);
    }

    public void loadNewsDetailsFromDb(DetailsFromDbListener listener){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<DetailedNews> query = realm.where(DetailedNews.class);
        RealmResults<DetailedNews> result = query.findAllAsync();
        DetailedNews detailedNews = result.first();
        listener.onLoaded(detailedNews);
    }
}
