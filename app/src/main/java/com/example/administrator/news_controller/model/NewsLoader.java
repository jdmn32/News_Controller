package com.example.administrator.news_controller.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.news_controller.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.administrator.news_controller.model.DbContract.CategoryEntries.CATEGORIES_URI;

public class NewsLoader implements INewsLoader {

    private Context context;
    private List<News.NewsItem> news;

    public static final String LOG_TAG = NewsLoader.class.getName();

    public NewsLoader(Context context) {
        this.context = context;
    }

    public interface NewsService{

        @GET("rss/all")
        Call<News> fetchNewsItems();
    }

    public interface NewsListener{
        void onLoaded(List<News.NewsItem> news);
    }

    public boolean canLoadMore(){
        if (news != null) {
            if (news.size() < 10) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void loadNews(final NewsLoader.NewsListener listener){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meduza.io/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        NewsLoader.NewsService newsService = retrofit.create(NewsLoader.NewsService.class);
        Call<News> call = newsService.fetchNewsItems();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()){
                    listener.onLoaded(response.body().getChannel().getNewsItems());
//                    saveToDataBase(news, id);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
            }
        });
    }

//    private void saveToDataBase(List<News.NewsItem> news, Integer id){
//        for (News.NewsItem newsItem : news) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(DbContract.NewsEntries.NEWS_ID, newsItem.getId());
//            contentValues.put(DbContract.NewsEntries.NEWS_TITLE, newsItem.getTitle());
//            contentValues.put(DbContract.NewsEntries.NEWS_DATE, newsItem.getDate());
//            contentValues.put(DbContract.NewsEntries.NEWS_DESCRIPTION, newsItem.getShortDescription());
//            contentValues.put(DbContract.NewsEntries.CATEGORY_ID, id);
//            newsItem.setCategoryId(id);
//
//            DbHelper dbHelper = new DbHelper(context);
//            SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
//            try {
//                dataBase.insertWithOnConflict(DbContract.NewsEntries.TABLE_NAME, null, contentValues, 5);
//            } catch (SQLiteConstraintException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
