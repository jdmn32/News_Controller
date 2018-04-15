package com.example.administrator.news_controller.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.news_controller.DetailedNews;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.administrator.news_controller.model.DbContract.CategoryEntries.CATEGORIES_URI;

public class NewsDetailsLoader implements INewsDetailsLoader {

    private Context context;
    private DetailsService detailsService;

    public static final String LOG_TAG = NewsDetailsLoader.class.getName();

    public NewsDetailsLoader(Context context) {
        this.context = context;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meduza.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        detailsService = retrofit.create(DetailsService.class);
    }

    public interface DetailsService {

        @GET("/api/v3/{path}")
        Call<DetailedNews> fetchDetails(@Path("path") String newsPath);
    }

    public interface DetailsListener {
        void onLoaded(DetailedNews detailedNews);
    }

    public void loadDetails(String newsPath, final NewsDetailsLoader.DetailsListener listener) {
        detailsService.fetchDetails(newsPath).enqueue(new Callback<DetailedNews>() {
            @Override
            public void onResponse(Call<DetailedNews> call, Response<DetailedNews> response) {
                if (response.isSuccessful()) {
                    Log.e(LOG_TAG, String.valueOf(response.body()));
                    listener.onLoaded(response.body());
//                    saveToDataBase(detailedNewsItem, categoryId);
                }
            }

            @Override
            public void onFailure(Call<DetailedNews> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
            }
        });
    }

//    private void saveToDataBase(DetailedNews.DetailedNewsItem detailedNewsItem, Integer categoryId){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DbContract.NewsEntries.NEWS_ID, detailedNewsItem.getId());
//        contentValues.put(DbContract.NewsEntries.NEWS_TITLE, detailedNewsItem.getTitle());
//        contentValues.put(DbContract.NewsEntries.NEWS_DATE, detailedNewsItem.getDate());
//        contentValues.put(DbContract.NewsEntries.NEWS_DESCRIPTION, detailedNewsItem.getShortDescription());
//        contentValues.put(DbContract.NewsEntries.FULL_DESCRIPTION, detailedNewsItem.getFullDescription());
//        contentValues.put(DbContract.NewsEntries.CATEGORY_ID, categoryId);
//
//        DbHelper dbHelper = new DbHelper(context);
//        SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
//        try {
//            dataBase.insertWithOnConflict(DbContract.NewsEntries.TABLE_NAME, null, contentValues, 5);
//        } catch (SQLiteConstraintException e) {
//            e.printStackTrace();
//        }
//    }
}
