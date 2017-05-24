package com.example.companyreader;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sargiskh on 5/16/2017.
 */

public class CompanyCursorAdapter extends RecyclerView.Adapter<CompanyCursorAdapter.CompanyReaderViewHolder> {

    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String WORK_EXPERIENCE = "work_experience";

    private Cursor cursor;

    public CompanyCursorAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public CompanyReaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.company_reader_recycler_view_item_layout, parent, false);
        CompanyReaderViewHolder companyReaderViewHolder = new CompanyReaderViewHolder(view);
        return companyReaderViewHolder;
    }

    @Override
    public void onBindViewHolder(CompanyReaderViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public void updateData(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }


    public class CompanyReaderViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewAge;
        private TextView textViewWorkExperience;

        public CompanyReaderViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.text_view_name);
            textViewAge = (TextView) itemView.findViewById(R.id.text_view_age);
            textViewWorkExperience = (TextView) itemView.findViewById(R.id.text_view_work_experience);
        }

        public void bind(int position) {
            cursor.moveToPosition(position);
            textViewName.setText(cursor.getString(cursor.getColumnIndex(NAME)));
            textViewAge.setText(cursor.getString(cursor.getColumnIndex(AGE)));
            if (cursor.getColumnIndex(WORK_EXPERIENCE) != -1) {
                textViewWorkExperience.setVisibility(View.VISIBLE);
                textViewWorkExperience.setText("" + cursor.getString(cursor.getColumnIndex(WORK_EXPERIENCE)));
            } else {
                textViewWorkExperience.setVisibility(View.GONE);
            }
        }
    }
}
