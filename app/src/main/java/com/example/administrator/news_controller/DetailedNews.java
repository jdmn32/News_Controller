package com.example.administrator.news_controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailedNews {

    public DetailedNews(Root root) {
        this.root = root;
    }

    @SerializedName("root")
    private Root root;

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }


    public static class Root {

        public Root(String url, String shareImage, Content content, String title) {
            this.url = url;
            this.shareImage = shareImage;
            this.content = content;
            this.title = title;
        }

        @SerializedName("url")
        private String url;

        @SerializedName("share_image")
        private String shareImage;

        @SerializedName("content")
        private Content content;

        @SerializedName("title")
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getShareImage() {
            return shareImage;
        }

        public void setShareImage(String shareImage) {
            this.shareImage = shareImage;
        }

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Content {

        public Content(String body) {
            this.body = body;
        }

        @SerializedName("body")
        @Expose
        private String body;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
