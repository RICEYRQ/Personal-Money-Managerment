package com.example.riceyrq.personalmoneymanagerment.acitvity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.riceyrq.personalmoneymanagerment.R;
import com.example.riceyrq.personalmoneymanagerment.acitvity.Main;

public class ToolbarFragment extends Fragment {
    public Toolbar toolbar;
    public String username = "";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        Main activity = (Main) getActivity();
        activity.drawer.setToolbar(activity, toolbar, true);
        username = activity.getUsername();
        Log.w("test", username);
    }
}