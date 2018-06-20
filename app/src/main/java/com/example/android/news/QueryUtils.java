package com.example.android.news;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String baseUrl = "https://content.guardianapis.com/search";


    private QueryUtils() {
        //empty constructor for util class
    }

    private static String appendSectionToBaseUrl(Section section){
        String sectionId = section.getSectionId();
        Uri baseUri = Uri.parse(baseUrl);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api-key","test");
        uriBuilder.appendQueryParameter("section",sectionId);
        uriBuilder.appendQueryParameter("show-tags","contributor");

        return uriBuilder.toString();
    }

    public static List<NewsItem> fetchNewsItems(Section section){
        String urlString = appendSectionToBaseUrl(section);
        URL requesturl = createUrl(urlString);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(requesturl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<NewsItem> newsList = extractNewsItemsFromJson(jsonResponse);
        return newsList;
    }

    private static List<NewsItem> extractNewsItemsFromJson(String jsonResponse) {
        if(jsonResponse==null || jsonResponse.isEmpty())
            return null;

        List<NewsItem> newsItemsList = new ArrayList<>();

        try {
            JSONObject rootJsonObject = new JSONObject(jsonResponse);
            JSONObject responseJsonObject = rootJsonObject.getJSONObject("response");
            JSONArray newsJsonArray = responseJsonObject.getJSONArray("results");

            for (int i=0;i<newsJsonArray.length();i++){
                JSONObject newsItemJsonObject = newsJsonArray.getJSONObject(i);

                String newsItemid = newsItemJsonObject.getString("id");
                String newsTitle = newsItemJsonObject.getString("webTitle").split("\\|")[0]; //title may sometimes contain author separated by |;
                String webUrl = newsItemJsonObject.getString("webUrl");
                String webPublishedDate = newsItemJsonObject.getString("webPublicationDate");
                String sectionName = newsItemJsonObject.getString("sectionName");
                String sectionId = newsItemJsonObject.getString("sectionId");

                //tags json array & authors
                JSONArray tagsJsonArray = newsItemJsonObject.getJSONArray("tags");
                List<String> authors = new ArrayList <>();
                for(int k=0;k<tagsJsonArray.length();k++){
                    JSONObject tagsJsonObject = tagsJsonArray.getJSONObject(k);
                    String partialAuthor = tagsJsonObject.getString("webTitle");
                    authors.add(partialAuthor);
                }

                // construct newsitems list
                NewsItem currentNewsItem = new NewsItem(newsItemid,newsTitle,webUrl);
                currentNewsItem.setWebPublicationDate(webPublishedDate);
                currentNewsItem.setmSection(new Section(sectionName,sectionId));
                currentNewsItem.setAuthor(authors);

                newsItemsList.add(currentNewsItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsItemsList;
    }

    private static String makeHttpRequest(URL requesturl) throws IOException {
        String jsonResponse = null;
        if(requesturl==null)
            return jsonResponse;

        HttpURLConnection httpconnection = null;
        InputStream inputStream = null;

        try {
            httpconnection = (HttpURLConnection) requesturl.openConnection();
            httpconnection.setRequestMethod("GET");
            httpconnection.setReadTimeout(10000);
            httpconnection.setConnectTimeout(150000);
            httpconnection.connect();

            if(httpconnection.getResponseCode() == 200){
                inputStream = httpconnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else {
                Log.e(LOG_TAG,"http connection response is not 200 ok");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(httpconnection!=null){
                httpconnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder jsonResponse = new StringBuilder();

        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();
            while(line!=null){
                jsonResponse.append(line);
                line = reader.readLine();
            }
        }
        return jsonResponse.toString();
    }

    private static URL createUrl(String urlString) {
        URL url = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
