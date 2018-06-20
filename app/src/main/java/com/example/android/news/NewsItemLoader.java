package com.example.android.news;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsItemLoader extends AsyncTaskLoader<List<NewsItem>> {
    private Section newsSectionObject;

    public NewsItemLoader(Context context, Section newsSectionObject) {
        super(context);
        this.newsSectionObject = newsSectionObject;
    }

    @Override
    public List <NewsItem> loadInBackground() {
        if (newsSectionObject==null){
            return null;
        }

        List<NewsItem> newsItemsList = QueryUtils.fetchNewsItems(newsSectionObject);
        return newsItemsList;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
