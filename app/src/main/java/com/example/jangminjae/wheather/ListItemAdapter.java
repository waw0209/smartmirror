package com.example.jangminjae.wheather;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dohee on 16. 12. 5.
 */

public class ListItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<TextItem> mItems = new ArrayList<TextItem>();

    public ListItemAdapter(Context context) {
        mContext = context;
    }

    public void addItem(TextItem it) {
        mItems.add(it);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListItemView itemView = null;

        if (view == null) {
            itemView = new ListItemView(mContext, mItems.get(i));
        } else {
            itemView = (ListItemView)view;
            itemView.setText(0, mItems.get(i).getData(0));
            itemView.setText(1, mItems.get(i).getData(1));
            itemView.setText(2, mItems.get(i).getData(2));
            itemView.setText(3, mItems.get(i).getData(3));
        }

        return itemView;
    }

}
