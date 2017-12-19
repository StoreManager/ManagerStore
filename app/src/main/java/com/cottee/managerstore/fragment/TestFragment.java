package com.cottee.managerstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.cottee.managerstore.R;


public class TestFragment extends Fragment {
    private GridView gv_entertainment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);
        gv_entertainment = view.findViewById(R.id.gv_entertainment);
        return view;
    }
}
