package com.fivefactory.buyit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

/**
 * Coded by Sotti on 27/08/13.
 * Nobody creates nothing.
 * Just for fun.
 */

public class Settings_Privacy extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_privacy);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action buttons
        switch(item.getItemId())
        {
            case R.id.profile:
                Intent profile_intent = new Intent(Settings_Privacy.this,Profile.class);
                startActivity(profile_intent);
                break;

            case R.id.send_feedback:
                Intent  sendFeedback_intent = new Intent(Settings_Privacy.this, Settings_SendFeedback.class);
                startActivity(sendFeedback_intent);
                break;

            case R.id.about:
                Intent about_intent = new Intent(Settings_Privacy.this,About.class);
                startActivity(about_intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
