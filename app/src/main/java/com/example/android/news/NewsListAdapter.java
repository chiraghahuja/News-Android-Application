package com.example.android.news;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class NewsListAdapter extends ArrayAdapter<NewsItem> {

    public NewsListAdapter(@NonNull Context context, @NonNull List<NewsItem> newsItemsList) {
        super(context, 0, newsItemsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        Context context = getContext();
        Resources resources = context.getResources();
        if(currentView ==null){
            currentView = LayoutInflater.from(context).inflate(R.layout.newslistitem, parent,false);
        }

        NewsItem currentNewsItem = this.getItem(position);

        String title = currentNewsItem.getTitle();
        TextView newsHeadlineTextView = currentView.findViewById(R.id.newsheadline_textview);
        newsHeadlineTextView.setText(title);

        List<String> author = currentNewsItem.getAuthor();
        if(author!=null && !author.isEmpty()) {
            TextView authorTextView = currentView.findViewById(R.id.author_textview);
            String authorsFormattedString = formatAuthorList(author);
            authorTextView.setText(authorsFormattedString);
            authorTextView.setVisibility(View.VISIBLE);
        }

        String rawPublishedDate = currentNewsItem.getWebPublicationDate();
        TextView dateTextView = currentView.findViewById(R.id.datepublished_textview);
        dateTextView.setText(formatDate(rawPublishedDate));

        return currentView;
    }

    private String formatAuthorList(List<String> author) {
        String combinedAuthorsString =  "By " + TextUtils.join(" & ",author);
        return combinedAuthorsString;
    }

    private String formatSection(Section section) {
        String sectionName = section.getSectionName();
        return sectionName + " Section";
    }

    private String formatDate(String rawPublishedDate) {
        String truncateRawDate = rawPublishedDate.substring(0,11);

        DateFormat convertStringFormatter = new SimpleDateFormat("yyyy-dd-MM");
        DateFormat publishFormat = new SimpleDateFormat("d MMM yyyy");
        String dateToDisplay = new String();
        try {
            Date date = convertStringFormatter.parse(truncateRawDate); //convert string to date format
            dateToDisplay = publishFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Published: " + dateToDisplay;
    }
}
