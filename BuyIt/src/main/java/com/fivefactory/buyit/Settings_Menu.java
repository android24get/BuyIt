package com.fivefactory.buyit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Coded by Sotti on 27/08/13.
 * Nobody creates nothing.
 * Just for fun.
 */

public class Settings_Menu extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);
    }

    public void privacySettings_launcher(View view)
    {
        Intent  privacySettings_intent = new Intent(view.getContext(), Settings_Privacy.class);
        startActivity(privacySettings_intent);
    }

    public void logOut_launcher(View view)
    {
        Intent  logOut_intent = new Intent(view.getContext(), LogOut.class);
        startActivity(logOut_intent);
    }

    public void launch_sendFeedback(View view)
    {
        Intent  logOut_intent = new Intent(view.getContext(), Settings_SendFeedback.class);
        startActivity(logOut_intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Pass the event to ActionBarDrawerToggle, if it returns true, then it has handled the app icon touch event
        /*if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }*/

        // Handle action buttons
        switch(item.getItemId())
        {
            case R.id.profile:
                Intent profile_intent = new Intent(Settings_Menu.this,Profile.class);
                startActivity(profile_intent);
                break;

            case R.id.send_feedback:
                Intent  sendFeedback_intent = new Intent(Settings_Menu.this, Settings_SendFeedback.class);
                startActivity(sendFeedback_intent);
                break;

            case R.id.about:
                Intent about_intent = new Intent(Settings_Menu.this,About.class);
                startActivity(about_intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}