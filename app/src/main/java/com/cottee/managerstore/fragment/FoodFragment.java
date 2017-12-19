package com.cottee.managerstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.FoodStyleGrivdViewAdapter;

import java.util.List;


public class FoodFragment extends Fragment {


    private GridView gv_food;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.food_fragment, container, false);
        gv_food = view.findViewById(R.id.gv_food);
        return view;
    }
    public void initFoodData(List<String> foodList){
        gv_food.setAdapter(new FoodStyleGrivdViewAdapter(getContext(),foodList));

    }


}
