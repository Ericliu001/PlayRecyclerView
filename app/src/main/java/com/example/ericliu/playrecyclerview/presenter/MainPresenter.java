package com.example.ericliu.playrecyclerview.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ericliu.playrecyclerview.adapter.CustomAdapter;
import com.example.ericliu.playrecyclerview.model.Person;
import com.example.ericliu.playrecyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric.liu on 27/05/15.
 */
public class MainPresenter implements ListPresenter {
    Context mContext;
    volatile List<Person> personList;
    String[] nameArray;
    MainFace mFace;
    boolean isLoading = false;

    public static final int NUM_OF_ITEMS_TO_LOAD = 8;


    public MainPresenter(Context context, MainFace face) {
        mContext = context;

        mFace = face;

        personList = new ArrayList<Person>();

        nameArray = mContext.getResources().getStringArray(R.array.names);

        loadFirstRound();
    }

    public CustomAdapter.FooterModel getFooterItemAtPosition(int position) {
        CustomAdapter.FooterModel footerModel = new CustomAdapter.FooterModel();
        if (position < nameArray.length) {
            footerModel.isProgressBarShown = true;
            footerModel.isTextViewShown = false;
            footerModel.text = "";
        } else {
            footerModel.isProgressBarShown = false;
            footerModel.isTextViewShown = true;
            footerModel.text = "last row reached.";
        }

        return footerModel;
    }

    public RecyclerView.ViewHolder getCustomViewHolder(View itemView) {
        return new PersonViewHolder(itemView);
    }

    public void onAddItemButtonClicked() {
        mFace.changeAddItemButtonState();
    }


    public interface MainFace {
        void onFinishLoad();
        void changeAddItemButtonState();
    }


    void onItemClicked(Person person) {
        Toast.makeText(mContext, "You've clicked " + person.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostViewCreated() {
    }

    private void loadFirstRound() {
        personList.clear();

        onLoadMore();
    }


    public synchronized void onLoadMore() {

        int startIndex = personList.size();
        int endIndex = startIndex + NUM_OF_ITEMS_TO_LOAD - 1;

        if (startIndex > nameArray.length - 1) {
            isLoading = false;
            return;
        }

        if (endIndex > nameArray.length - 1) {
            endIndex = nameArray.length - 1;
        }

        Integer[] params = {startIndex, endIndex};

        if (isLoading == false) {
            isLoading = true;

            new LoadMoreDataTask(nameArray, personList, this).execute(params);
        }


    }

    public int getDataListSize() {
        return personList.size();
    }

    public Person getItemAtPosition(int position) {
        return personList.get(position);
    }

    public int getMaximunSize(){
        return nameArray.length;
    }


    private static class LoadMoreDataTask extends AsyncTask<Integer, Void, List> {
        String[] nameArray;
        List<Person> personList;
        MainPresenter mainPresenter;


        public LoadMoreDataTask(String[] names, List<Person> list, MainPresenter presenter) {
            nameArray = names;
            personList = list;
            mainPresenter = presenter;
        }


        @Override
        protected List doInBackground(Integer... params) {
            int startIndex = params[0];
            int endIndex = params[1];
            List<Person> appendedList = new ArrayList<>();

            for (int i = startIndex; i <= endIndex; i++) {
                Person person = new Person();
                person.setName(nameArray[i]);
                appendedList.add(person);
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return appendedList;
        }


        @Override
        protected void onPostExecute(List appendedList) {
            personList.addAll(appendedList);
            mainPresenter.onLoadFinished();
        }
    }

    private void onLoadFinished() {
        mFace.onFinishLoad();
        isLoading = false;
    }


    public class PersonViewHolder extends CustomAdapter.CustomViewHolder<Person>{
        public ImageView icon;
        public TextView title;

        public PersonViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        @Override
        protected void refreshView() {
            title.setText(t.getName());
        }


    }
}
