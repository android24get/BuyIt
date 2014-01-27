package com.fivefactory.buyit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Coded by Sotti on 27/08/13.
 * Nobody creates nothing.
 * Just for fun.
 */

public class LogOut extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_out);
    }

    public void launch_logIn_SignUp(View view)
    {
        Intent logIn_SignUp_intent = new Intent(view.getContext(),SignInActivity.class);
        startActivity(logIn_SignUp_intent);
    }
}
