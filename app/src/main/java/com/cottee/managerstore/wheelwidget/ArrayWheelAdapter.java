package com.cottee.managerstore.wheelwidget;

/**
 * Created by Administrator on 2017/5/30.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cottee.managerstore.R;


/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> extends AbstractWheelTextAdapter {

    // items
    private String[] items;

    /**
     * Constructor
     * @param context the current context
     * @param items the items
     */
    /**
     * Constructor
     * @param context the current context
     * @param items the items
     */
    public ArrayWheelAdapter(Context context, String[] items, int currentItem, int maxsize, int minsize) {
        super(context, R.layout.layout_timewheel_present_time, NO_RESOURCE, currentItem, maxsize, minsize);

        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.items = items;
        setItemTextResource(R.id.tempValue);
    }
    @Override
    public View getItem(int index, View cachedView, ViewGroup parent) {
        View view = super.getItem(index, cachedView, parent);
        return view;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.length) {
            String item = items[index];
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.length;
    }
}

