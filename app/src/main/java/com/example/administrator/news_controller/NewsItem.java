package com.example.administrator.news_controller;

import org.simpleframework.xml.*;
import org.simpleframework.xml.Root;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;

@Root(name = "item", strict = false)
public class NewsItem extends RealmObject {

    @Path("title")
    @Text(required = false)
    private String title;

    @Element(name = "description", required = false)
    private String description;

    @Element(name = "pubDate", required = false)
    private String time;

    @Element(name = "link", required = false)
    private String url;

    @Path("enclosure")
    @Attribute(name = "url", required = false)
    private String enclosureImageUrl;

    public NewsItem() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        DateFormat sdfToDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZZZ", Locale.ENGLISH);
        DateFormat sdfToString = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        String newsPublishingDate = null;
        Date pubDate;
        try {
            pubDate = sdfToDate.parse(time);
            newsPublishingDate = sdfToString.format(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newsPublishingDate;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        if (enclosureImageUrl != null) {
            return enclosureImageUrl;
        } else {
            return null;
        }
    }
}

