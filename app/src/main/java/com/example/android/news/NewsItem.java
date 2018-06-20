package com.example.android.news;

import java.io.Serializable;
import java.util.List;

public class NewsItem implements Serializable {
    private String id;
    private Section mSection;
    private String webPublicationDate;
    private String title;
    private String webUrl;

    private List<String> author;

    public NewsItem(String id, String title, String webUrl) {
        this.id = id;
        this.title = title;
        this.webUrl = webUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public Section getmSection() {
        return mSection;
    }

    public void setmSection(Section mSection) {
        this.mSection = mSection;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsItem newsItem = (NewsItem) o;

        return getTitle().equals(newsItem.getTitle());
    }

    @Override
    public int hashCode() {
        return getTitle().hashCode();
    }
}
