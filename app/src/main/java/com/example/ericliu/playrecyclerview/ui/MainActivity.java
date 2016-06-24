package com.example.ericliu.playrecyclerview.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ericliu.playrecyclerview.AutoLoadScrollListener;
import com.example.ericliu.playrecyclerview.R;
import com.example.ericliu.playrecyclerview.adapter.CustomAdapter;
import com.example.ericliu.playrecyclerview.presenter.MainPresenter;


public class MainActivity extends BaseActivity implements MainPresenter.MainFace, View.OnClickListener {

    MainPresenter mPresenter;

    RecyclerView mRecyclerView;
    CustomAdapter mAdapter;
    FloatingActionButton btAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btAdd = (FloatingActionButton) findViewById(R.id.btAdd);
        btAdd.setOnClickListener(this);

        mPresenter = new MainPresenter(MainActivity.this, this);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mAdapter = new CustomAdapter(mPresenter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setOnScrollListener(new CustomScrollListener());


    }

    @Override
    public void onFinishLoad() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void changeAddItemButtonState() {
        if (btAdd.isSelected()) {
            btAdd.setSelected(false);
        } else {
            btAdd.setSelected(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btAdd) {
            mPresenter.onAddItemButtonClicked();
        }
    }


    private class CustomScrollListener extends AutoLoadScrollListener {


        @Override
        public void onLoadMore() {
            mPresenter.onLoadMore();

        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
