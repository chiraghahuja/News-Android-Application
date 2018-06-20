package com.example.android.news;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsCategoryFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks <List<NewsItem>> {


    //state of the fragment
    private android.support.v4.app.LoaderManager mLoaderManager;
    private Context mContext;
    public int loaderId;
    public Section mSection;

    private ArrayList<NewsItem> newsItemsArrayList;
    private NewsListAdapter mListAdapter;
    private ListView newsListView;
    private ProgressBar newsLoadingBar;
    private TextView emptyTextView;

    public NewsCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();

        //check if there is internet connection. else do not spin off a loader
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getApplicationContext().getSystemService(mContext.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            /*spin off a loader*/
            Bundle b = new Bundle();
            b.putSerializable("sectionobject", mSection);
            mLoaderManager.restartLoader(loaderId, b, NewsCategoryFragment.this);/*diff between restart loader and init loader: https://stackoverflow.com/q/14445070*/
        }
        else{
            emptyTextView.setText(R.string.no_internet);
            mListAdapter.clear();
            mListAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //init
        mContext = getContext();
        mLoaderManager = getLoaderManager();

        // get arguments passed to the fragment
        Bundle bundle = getArguments();
        loaderId = bundle.getInt("tabserialnumber");
        mSection = new Section(SectionPageAdapter.sectionNames[loaderId], SectionPageAdapter.sectionId[loaderId]); // get section name and id for tabserial number. we need this session object to pass it to loader and queryutils


        // setup the listview
        View rootView = inflater.inflate(R.layout.category_fragment,container,false);
        ListView newsListView = (ListView) rootView.findViewById(R.id.newscategory_listview);
        newsItemsArrayList = new ArrayList <>();
        mListAdapter = new NewsListAdapter(mContext,newsItemsArrayList);
        newsListView.setAdapter(mListAdapter);

        emptyTextView = rootView.findViewById(R.id.empty_textview);
        newsListView.setEmptyView(emptyTextView);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                NewsItem newsItem = mListAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.getWebUrl()));
                if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        newsLoadingBar = (ProgressBar) rootView.findViewById(R.id.newsloading_bar);
        newsLoadingBar.setVisibility(View.GONE);


        return rootView;
    }

   public static NewsCategoryFragment newInstance(int tabNumber) {
        Bundle args = new Bundle();
        args.putInt("tabserialnumber",tabNumber);
        NewsCategoryFragment fragment = new NewsCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public android.support.v4.content.Loader<List <NewsItem>> onCreateLoader(int id, Bundle args) {
        newsLoadingBar.setVisibility(View.VISIBLE);
        return new NewsItemLoader(mContext, (Section) args.getSerializable("sectionobject"));
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List <NewsItem>> loader, List <NewsItem> data) {
        newsLoadingBar.setVisibility(View.GONE);
        emptyTextView.setText(R.string.no_results_search);
        mListAdapter.clear();
        if(data!=null) {
            mListAdapter.addAll(data);
        }
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List <NewsItem>> loader) {
        //mListAdapter.clear();
        mListAdapter.notifyDataSetChanged();
    }
}
