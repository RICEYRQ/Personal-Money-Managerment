package com.example.riceyrq.personalmoneymanagerment.acitvity.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.riceyrq.personalmoneymanagerment.R;

public class MonthPicFragment extends ToolbarFragment {

    public MonthPicFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_month_pic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.monthPic);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}