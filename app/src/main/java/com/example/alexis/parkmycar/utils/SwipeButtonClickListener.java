package com.example.alexis.parkmycar.utils;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;


/**
 * Created by Alexis on 22/03/2017.
 */

public class SwipeButtonClickListener implements View.OnClickListener
{
    private LinearLayout leftLayout, rightLayout, mainLayout;
    private ArrayAdapter adapter;
    private int position;

    public SwipeButtonClickListener(LinearLayout leftlayout, LinearLayout rightLayout, LinearLayout main, int position, ArrayAdapter adapter)
    {
        this.leftLayout = leftlayout;
        this.rightLayout = rightLayout;
        this.mainLayout = main;
        this.adapter = adapter;
        this.position = position;
    }
    @Override
    public void onClick(View view) {

    }

    public ArrayAdapter getAdapter()
    {
        return this.adapter;
    }

    public int getPosition()
    {
        return this.position;
    }

    public LinearLayout getLeftLayout()
    {
        return  this.leftLayout;
    }

    public LinearLayout getRightLayout()
    {
        return this.rightLayout;
    }

    public LinearLayout getMainLayout()
    {
        return this.mainLayout;
    }
}
