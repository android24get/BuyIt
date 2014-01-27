package com.fivefactory.buyit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;

/**
 * Coded by Sotti on 7/09/13.
 * Nobody creates nothing.
 * Just for fun.
 */
public class LocalInfo_McDonalds extends Activity
{
    private ShareActionProvider mShareActionProvider;
    int phone = 915664100;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_information_mcdonalds);
    }

    public void launch_map_intent(View view)
    {
        Intent maps_intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=el+prat+de+llobregat+airport"));
        startActivity(maps_intent);
    }

    public void launch_call_intent(View view)
    {
        Intent call_intent = new Intent(Intent.ACTION_DIAL);
        call_intent.setData(Uri.parse("tel:"+phone));
        startActivity(call_intent);
    }

    public void launch_web_intent(View view)
    {
        Intent official_web_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mcdonalds.com"));
        startActivity(official_web_intent);
    }

    public void launch_local_moreinfo(View view)
    {
        String wikipedia_url = getResources().getString(R.string.wikipedia_url_mcdonalds);
        Intent more_localinfo_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(wikipedia_url));
        startActivity(more_localinfo_intent);
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent)
    {
        if (mShareActionProvider != null)
        {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.local_info, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);
        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        mShareActionProvider.setShareIntent(getDefaultIntent());

        return super.onCreateOptionsMenu(menu);
    }

    private Intent getDefaultIntent()
    {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");

        return sendIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action buttons
        switch(item.getItemId())
        {
            case R.id.settings:
                Intent settings_intent = new Intent(LocalInfo_McDonalds.this,Settings_Menu.class);
                startActivity(settings_intent);
                break;

            case R.id.profile:
                Intent profile_intent = new Intent(LocalInfo_McDonalds.this,Profile.class);
                startActivity(profile_intent);
                break;

            case R.id.send_feedback:
                Intent  sendFeedback_intent = new Intent(LocalInfo_McDonalds.this, Settings_SendFeedback.class);
                startActivity(sendFeedback_intent);
                break;

            case R.id.about:
                Intent about_intent = new Intent(LocalInfo_McDonalds.this,About.class);
                startActivity(about_intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
