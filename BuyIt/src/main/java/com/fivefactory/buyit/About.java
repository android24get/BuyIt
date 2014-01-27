package com.fivefactory.buyit;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Coded by Sotti on 27/08/13.
 * Nobody creates nothing.
 * Just for fun.
 */

public class About extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.about);
        getWindow().setFeatureDrawable(Window.FEATURE_LEFT_ICON, getResources().getDrawable(R.drawable.about));
    }
}
