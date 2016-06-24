package com.example.ericliu.playrecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ericliu.playrecyclerview.R;
import com.example.ericliu.playrecyclerview.presenter.ListPresenter;
import com.example.ericliu.playrecyclerview.presenter.MainPresenter;

/**
 * Created by eric.liu on 27/05/15.
 */

public class CustomAdapter extends RecyclerView.Adapter {

    private static final int ITEM_VIEW_TYPE_DATA = 0;
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;
    private final ListPresenter mPresenter;

    public static abstract class CustomViewHolder<T> extends RecyclerView.ViewHolder  {
        protected T t;



        public CustomViewHolder(View itemView) {
            super(itemView);

        }


        public void setItemData(T t) {
            this.t = t;
            refreshView();
        }

        protected abstract void refreshView();
    }


    public static class FooterModel {
       public  boolean isProgressBarShown = false;
       public  boolean isTextViewShown = false;
       public  String text;
    }


    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public TextView tvFooter;

        public FooterViewHolder(View itemView) {
            super(itemView);

            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            tvFooter = (TextView) itemView.findViewById(R.id.tvFooter);

        }

       public void setItemData(FooterModel footerModel){
           progressBar.setVisibility(footerModel.isProgressBarShown ? View.VISIBLE : View.INVISIBLE);
           tvFooter.setVisibility(footerModel.isTextViewShown ? View.VISIBLE : View.INVISIBLE);
           tvFooter.setText(footerModel.text);
       }
    }


    public CustomAdapter(MainPresenter presenter) {

        this.mPresenter = presenter;
    }


    @Override
    public int getItemViewType(int position) {
        return (position >= mPresenter.getDataListSize())
                ? ITEM_VIEW_TYPE_FOOTER
                : ITEM_VIEW_TYPE_DATA;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        RecyclerView.ViewHolder holder;

        if (viewType == ITEM_VIEW_TYPE_DATA) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
            holder = mPresenter.getCustomViewHolder(itemView);
            return holder;
        } else if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
            holder = new FooterViewHolder(itemView);
            return holder;
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (ITEM_VIEW_TYPE_DATA == getItemViewType(position)) {
            CustomViewHolder customHolder = ((CustomViewHolder) holder);
            customHolder.setItemData(mPresenter.getItemAtPosition(position));

        }else if (ITEM_VIEW_TYPE_FOOTER == getItemViewType(position)) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.setItemData(mPresenter.getFooterItemAtPosition(position));
        }
    }

    @Override
    public int getItemCount() {
        return mPresenter.getDataListSize() + 1;
    }
}