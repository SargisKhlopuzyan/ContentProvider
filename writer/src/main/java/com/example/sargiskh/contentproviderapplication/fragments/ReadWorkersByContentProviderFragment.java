package com.example.sargiskh.contentproviderapplication.fragments;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.sargiskh.contentproviderapplication.R;
import com.example.sargiskh.contentproviderapplication.adapters.MyCPRecyclerViewAdapter;
import com.example.sargiskh.contentproviderapplication.content_provider.MyContentProvider;

public class ReadWorkersByContentProviderFragment extends Fragment {

    public static final String AUTHORITY = "company.content.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_WORKERS = "workers";
    // TaskEntry content URI = base content URI + path
    public static final Uri CONTENT_URI_WORKERS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORKERS).build();

    private Button refreshButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private static boolean IS_DATA_LOADING = false;

    private MyContentObserver myContentObserver = new MyContentObserver(new Handler());
    private MyCPRecyclerViewAdapter adapter;
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_stuff_by_content_provider, container, false);
        FindViews(view);

        adapter = new MyCPRecyclerViewAdapter(null);

        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IS_DATA_LOADING) {
                    adapter.updateData(null);
                    CompanyAsyncTaskLoader companyAsyncTaskLoader = new CompanyAsyncTaskLoader(getActivity());
                    companyAsyncTaskLoader.startLoading();
                }
            }
        });

        adapter = new MyCPRecyclerViewAdapter(cursor);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // true if we want to be notified when connected Urls also changed
        getActivity().getContentResolver().registerContentObserver(CONTENT_URI_WORKERS, true, myContentObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
//        getActivity().getContentResolver().unregisterContentObserver(myContentObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getContentResolver().unregisterContentObserver(myContentObserver);
    }

    private void FindViews(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        refreshButton = (Button)view.findViewById(R.id.button_refresh);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
    }

    //AsyncTaskLoader part
    private class CompanyAsyncTaskLoader extends AsyncTaskLoader <Cursor> {

        public CompanyAsyncTaskLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            progressBar.setVisibility(View.VISIBLE);
            if (!IS_DATA_LOADING) {
                IS_DATA_LOADING = true;
                forceLoad();
            }
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor1 = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI_WORKERS, null, null, null, null);
            return cursor1;
        }

        @Override
        public void deliverResult(Cursor cursor1) {
            super.deliverResult(cursor1);

            progressBar.setVisibility(View.INVISIBLE);
            IS_DATA_LOADING = false;

            adapter = new MyCPRecyclerViewAdapter(cursor1);
            recyclerView.setAdapter(adapter);
            cursor = cursor1;
        }
    }
    //End of AsyncTaskLoader part

    //ContentObserver part
    public class MyContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            this.onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            //Write your code here
            //Whatever is written here will be executed whenever a change is made
            CompanyAsyncTaskLoader companyAsyncTaskLoader = new CompanyAsyncTaskLoader(getActivity());
            companyAsyncTaskLoader.startLoading();
        }
    }
    //End of ContentObserver part
}