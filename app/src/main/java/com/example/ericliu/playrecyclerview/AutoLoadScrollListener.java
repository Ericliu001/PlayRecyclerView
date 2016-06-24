package com.example.ericliu.playrecyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by eric.liu on 27/05/15.
 */
public abstract class AutoLoadScrollListener extends RecyclerView.OnScrollListener {

    int visibleItemCount  = 0;
    int totalItemCount = 0;
    int firstVisibleItem = 0;
    int previousTotal = 0;
    int visibleThreshold = 2; // number of items before reaching the bottom.

    boolean isLoading = false;


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

       LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!isLoading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached



            onLoadMore();

            isLoading = true;
        }
    }


    public abstract void onLoadMore();
}
