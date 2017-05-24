package com.example.sargiskh.contentproviderapplication.fragments;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sargiskh.contentproviderapplication.R;
import com.example.sargiskh.contentproviderapplication.content_provider.MyContentProvider;
import com.example.sargiskh.contentproviderapplication.database.MySQLiteOpenHelper;

public class WriteWorkerByContentProviderFragment extends Fragment {

    private EditText nameEditText;
    private EditText ageEditText;
    private EditText workExperienceEditText;
    private Button addButton;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_workers_by_content_provider, container, false);
        FindViews(view);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MySQLiteOpenHelper.NAME, nameEditText.getText().toString());
                contentValues.put(MySQLiteOpenHelper.AGE, Integer.parseInt(ageEditText.getText().toString()));
                contentValues.put(MySQLiteOpenHelper.WORK_EXPERIENCE, Integer.parseInt(workExperienceEditText.getText().toString()));
                getActivity().getContentResolver().insert(MyContentProvider.CONTENT_URI_WORKERS, contentValues);
            }
        });
        return view;
    }

    private void FindViews(View view) {
        nameEditText = (EditText) view.findViewById(R.id.edit_text_name);
        ageEditText = (EditText) view.findViewById(R.id.edit_text_age);
        workExperienceEditText = (EditText) view.findViewById(R.id.edit_text_work_experience);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        addButton = (Button)toolbar.findViewById(R.id.button_add);
    }
}
