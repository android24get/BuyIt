package com.fivefactory.buyit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Coded by Sotti on 25/08/13.
 * Nobody creates nothing.
 * Just for fun.
 */

public class Settings_SendFeedback extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_send_feedback);
    }

    public void launch_sendFeedback(View view)
    {
        this.finish();
        Toast.makeText(view.getContext(),getResources().getString(R.string.feedback_thanks),Toast.LENGTH_SHORT).show();
    }
}
