package com.example.companyreader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

public class StuffFragment extends Fragment {

    public static final String AUTHORITY = "company.content.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_STUFF = "stuff";

    // TaskEntry content URI = base content URI + path
    public static final Uri CONTENT_URI_STUFF = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUFF).build();

    private Button refreshButton;
    private ProgressBar progressBar;

    private CompanyCursorAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    private Cursor cursor = null;

    private static boolean IS_DATA_LOADING = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stuff, container, false);
        FindViews(view);

        recyclerViewAdapter = new CompanyCursorAdapter(cursor);

        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IS_DATA_LOADING) {
                    recyclerViewAdapter.updateData(null);
                    MyAsyncTaskLoader myAsyncTaskLoader = new MyAsyncTaskLoader(getActivity());
                    myAsyncTaskLoader.startLoading();
                }
            }
        });
        return view;
    }

    private void FindViews(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        refreshButton = (Button)view.findViewById(R.id.button_refresh);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
    }

    private class MyAsyncTaskLoader extends AsyncTaskLoader<Cursor> {

        public MyAsyncTaskLoader(Context context) {
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

        /**
         @Override
         protected void onStopLoading() {
         super.onStopLoading();
         }

         @Override
         public void onCanceled(Cursor cursor) {
         super.onCanceled(cursor);
         }

         @Override
         protected void onReset() {
         super.onReset();
         }
         */
        @Override
        public Cursor loadInBackground() {
            Cursor cursor1 = getActivity().getContentResolver().query(CONTENT_URI_STUFF, null, null, null, null);
            return cursor1;
        }

        @Override
        public void deliverResult(Cursor cursor) {
            super.deliverResult(cursor);
            progressBar.setVisibility(View.INVISIBLE);
            IS_DATA_LOADING = false;

            recyclerViewAdapter = new CompanyCursorAdapter(cursor);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }
}
