package com.example.sargiskh.contentproviderapplication.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sargiskh.contentproviderapplication.R;

/**
 * Created by sargiskh on 5/2/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter <MyRecyclerViewAdapter.CompanyViewHolder> {

    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String WORK_EXPERIENCE = "work_experience";

    Cursor cursor;

    public MyRecyclerViewAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context); ;
        View view = layoutInflater.inflate(R.layout.company_recycler_view_item_layout, parent, false);

        CompanyViewHolder companyViewHolder = new CompanyViewHolder(view);
        return companyViewHolder;
    }

    @Override
    public void onBindViewHolder(CompanyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public void updateData(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    class CompanyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView ageTextView;
        private TextView workExperienceTextView;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            ageTextView = (TextView) itemView.findViewById(R.id.text_view_age);
            workExperienceTextView = (TextView) itemView.findViewById(R.id.text_view_work_experience);
        }

        public void bind(int position) {
            cursor.moveToPosition(position);
            nameTextView.setText(cursor.getString(cursor.getColumnIndex(NAME)));
            ageTextView.setText("" + cursor.getString(cursor.getColumnIndex(AGE)));
            if (cursor.getColumnIndex(WORK_EXPERIENCE) != -1) {
                workExperienceTextView.setVisibility(View.VISIBLE);
                workExperienceTextView.setText("" + cursor.getString(cursor.getColumnIndex(WORK_EXPERIENCE)));
            } else {
                workExperienceTextView.setVisibility(View.GONE);
            }
        }
    }
}


