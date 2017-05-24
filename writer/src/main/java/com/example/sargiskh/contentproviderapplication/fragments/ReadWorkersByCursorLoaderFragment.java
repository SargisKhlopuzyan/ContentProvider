package com.example.sargiskh.contentproviderapplication.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.sargiskh.contentproviderapplication.R;

public class ReadWorkersByCursorLoaderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String AUTHORITY = "company.content.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_WORKERS = "workers";
    // TaskEntry content URI = base content URI + path
    public static final Uri CONTENT_URI_WORKERS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORKERS).build();
    //
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String WORK_EXPERIENCE = "work_experience";
    //

    private Button refreshButton;
    private ListView listView;

    private SimpleCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_read_stuff_by_cursor_loader, container, false);
        FindViews(view);

        if (adapter == null) {
            //THis also works fine just exclude id.
            /**
            String[] from = new String[] { NAME, AGE, WORK_EXPERIENCE };
            int[] to = new int[] { R.id.text_view_name, R.id.text_view_age, R.id.text_view_work_experience };
            adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_view_item_layout, null, from, to, 0);
            */

            // Fields from the database (projection)
            // Must include the _id column for the adapter to work
            String[] from = new String[] { ID, NAME, AGE, WORK_EXPERIENCE };
            // Fields on the UI to which we map

            int[] to = new int[] { R.id.text_view_id, R.id.text_view_name, R.id.text_view_age, R.id.text_view_work_experience };
            adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_view_item_layout, null, from, to, 0);


            fillData();
        }
        listView.setAdapter(adapter);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillData();
            }
        });

        return view;
    }

    private void FindViews(View view) {
        listView = (ListView)view.findViewById(R.id.list_view);
        refreshButton = (Button)view.findViewById(R.id.button_refresh);
    }

    private void fillData() {
//        getLoaderManager().initLoader(0, null, this); //This every time shows only cashed data
        getLoaderManager().restartLoader(0, null, this); //This every time update data
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.e("ZZZZZZZZ", "onCreateLoader");
//        String[] projection = { ID, NAME, AGE, WORK_EXPERIENCE };
//        CursorLoader cursorLoader = new CursorLoader(getActivity(), CONTENT_URI_WORKERS, projection, null, null, null);
        CursorLoader cursorLoader = new CursorLoader(getActivity(), CONTENT_URI_WORKERS, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.e("ZZZZZZZZ", "onLoaderReset");
        // data is not available anymore, delete reference
        adapter.swapCursor(null);
    }
}