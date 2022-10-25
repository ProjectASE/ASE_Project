package com.example.aseproject.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.aseproject.R;

import java.util.ArrayList;

public class GenericSpinnerAdapter extends BaseAdapter {

    public ArrayList<Object> myList;
    Context mContext;
    LayoutInflater mLayoutInflator;

    public GenericSpinnerAdapter(ArrayList<Object> myList, Context mContext) {
        this.myList = myList;
        this.mContext = mContext;
        mLayoutInflator = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int i) {
        return myList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (myList.get(i) instanceof  Gender)
        {
            View myView = mLayoutInflator.inflate(R.layout.generic_spinner_item_layout, null);
            TextView genderTextView = myView.findViewById(R.id.textView);
            genderTextView.setText(((Gender) myList.get(i)).getGender());

            return myView;
        }

        else if (myList.get(i) instanceof DateClass)
        {
            View myView = mLayoutInflator.inflate(R.layout.generic_spinner_item_layout, null);
            TextView genderTextView = myView.findViewById(R.id.textView);
            genderTextView.setText(((DateClass) myList.get(i)).getDate());

            return myView;
        }

        if (myList.get(i) instanceof  Month)
        {
            View myView = mLayoutInflator.inflate(R.layout.generic_spinner_item_layout, null);
            TextView genderTextView = myView.findViewById(R.id.textView);
            genderTextView.setText(((Month) myList.get(i)).getMonth());

            return myView;
        }

        if (myList.get(i) instanceof  Year)
        {
            View myView = mLayoutInflator.inflate(R.layout.generic_spinner_item_layout, null);
            TextView genderTextView = myView.findViewById(R.id.textView);
            genderTextView.setText(((Year) myList.get(i)).getYear());

            return myView;
        }

        return null;
    }
}
