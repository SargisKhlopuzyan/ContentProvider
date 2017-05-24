package com.example.sargiskh.contentproviderapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sargiskh.contentproviderapplication.R;
import com.example.sargiskh.contentproviderapplication.database.DbDataSource;

public class WriteStuffByDbQueryFragment extends Fragment {

    private DbDataSource dbDataSource;

    private EditText nameEditText;
    private EditText ageEditText;
    private Button addButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_stuff_by_db_query, container, false);
        FindViews(view);

        dbDataSource = new DbDataSource(getActivity());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameEditText.getText().toString().isEmpty() && !ageEditText.getText().toString().isEmpty()) {
                    dbDataSource.open();
                    long id = dbDataSource.addStuff(nameEditText.getText().toString(), Integer.parseInt(ageEditText.getText().toString()));
                    dbDataSource.close();
                }
            }
        });
        return view;
    }

    private void FindViews(View view) {
        nameEditText = (EditText) view.findViewById(R.id.edit_text_name);
        ageEditText = (EditText) view.findViewById(R.id.edit_text_age);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        addButton = (Button)toolbar.findViewById(R.id.button_add);
    }


}
