package com.example.riceyrq.personalmoneymanagerment.acitvity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.riceyrq.personalmoneymanagerment.R;
import com.example.riceyrq.personalmoneymanagerment.acitvity.Main;

public class ShowFragment extends ToolbarFragment {


    public ShowFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.show);
        Main activity = (Main) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}