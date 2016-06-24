package com.example.ericliu.playrecyclerview.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ericliu.playrecyclerview.adapter.CustomAdapter;

/**
 * Created by eric.liu on 29/05/15.
 */
public interface ListPresenter {

    /**
     * A common interface for all Presenters to implement
     */
    void onPostViewCreated();

    int getDataListSize();

    RecyclerView.ViewHolder getCustomViewHolder(View itemView);

    Object getItemAtPosition(int position);

    CustomAdapter.FooterModel getFooterItemAtPosition(int position);
}
