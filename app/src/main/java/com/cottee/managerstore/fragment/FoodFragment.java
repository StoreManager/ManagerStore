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


import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.StoreStyleActivity;
import com.cottee.managerstore.adapter.FoodStyleGrivdViewAdapter;

import java.util.List;


public class FoodFragment extends Fragment {


    private GridView gv_food;
    private List<String> food;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.food_fragment, container, false);
        food = StoreStyleActivity.getFood();
        gv_food = view.findViewById(R.id.gv_food);
        gv_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("storeStyle",food.get(i));
                System.out.println("11111111111111"+food.get(i));
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
            }
        });
        return view;
    }
    public void initFoodData(List<String> foodList){
        gv_food.setAdapter(new FoodStyleGrivdViewAdapter(getContext(),foodList));

    }


}
