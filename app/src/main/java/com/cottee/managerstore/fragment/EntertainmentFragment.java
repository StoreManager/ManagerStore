package com.cottee.managerstore.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.StoreStyleActivity;
import com.cottee.managerstore.adapter.EntertainmentStyleGridViewAdapter;

import java.util.List;


public class EntertainmentFragment extends Fragment {

    private GridView gv_entertainment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.entertainment_fragment, container, false);

        gv_entertainment = view.findViewById(R.id.gv_entertainment);
        final List<String> entertainments = StoreStyleActivity.getEntertainments();
        gv_entertainment.setAdapter(new EntertainmentStyleGridViewAdapter(getContext(), entertainments));
        gv_entertainment.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {

                Intent intent = new Intent();
                intent.putExtra("storeStyle",entertainments.get(i));
                System.out.println("11111111111111"+entertainments.get(i));
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();

            }
        });
        return view;
    }



}


