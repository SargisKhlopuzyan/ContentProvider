package com.example.companyreader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

public class WorkersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String AUTHORITY = "company.content.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_WORKERS = "workers";

    // TaskEntry content URI = base content URI + path
    public static final Uri CONTENT_URI_STUFF = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORKERS).build();
    private static final int LOADER_ID = 1;

    private Button refreshButton;
    private ProgressBar progressBar;

    private CompanyCursorAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    private Cursor cursor = null;

    private static boolean IS_DATA_LOADING = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workers, container, false);
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
                    getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null, WorkersFragment.this);
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


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<Cursor>(getActivity()) {

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
                Cursor cursor1 = getActivity().getContentResolver().query(CONTENT_URI_STUFF, null, null, null, null);
                return cursor1;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        progressBar.setVisibility(View.INVISIBLE);
        IS_DATA_LOADING = false;

        recyclerViewAdapter = new CompanyCursorAdapter(cursor);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
