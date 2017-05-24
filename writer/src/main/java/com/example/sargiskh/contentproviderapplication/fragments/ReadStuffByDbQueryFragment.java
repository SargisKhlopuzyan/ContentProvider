package com.example.sargiskh.contentproviderapplication.fragments;

import android.database.Cursor;
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

import com.example.sargiskh.contentproviderapplication.R;
import com.example.sargiskh.contentproviderapplication.adapters.MyRecyclerViewAdapter;
import com.example.sargiskh.contentproviderapplication.database.DbDataSource;

public class ReadStuffByDbQueryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private DbDataSource dataSource;

    private Button refreshButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private MyRecyclerViewAdapter recyclerViewAdapter;
    private Cursor cursor = null;

    private static boolean IS_DATA_LOADING = false;
    private static final int LOADER_ID = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_stuff_by_db_query, container, false);
        FindViews(view);

        dataSource = new DbDataSource(getActivity());

        recyclerViewAdapter = new MyRecyclerViewAdapter(cursor);

        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IS_DATA_LOADING) {
                    recyclerViewAdapter.updateData(null);
                    getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null, ReadStuffByDbQueryFragment.this);
//                    getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, ReadStuffByDbQueryFragment.this);
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
                dataSource.open();
                cursor = dataSource.getAllStuff();
                dataSource.close();
                return cursor;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        progressBar.setVisibility(View.INVISIBLE);
        IS_DATA_LOADING = false;
        recyclerViewAdapter = new MyRecyclerViewAdapter(cursor);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}