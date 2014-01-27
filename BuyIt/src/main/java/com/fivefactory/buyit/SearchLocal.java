package com.fivefactory.buyit;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Coded by Sotti on 27/08/13.
 * Nobody creates nothing.
 * Just for fun.
 */

public class SearchLocal extends Activity
{
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.local_search_result_list);

        // Defined Array values to show in ListView
        String[] locals = getResources().getStringArray(R.array.locals_array);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, locals);

        //Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {

                // ListView Clicked item index
                //int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Intent backToMain_Intent = new Intent(view.getContext(),MainActivity.class);
                backToMain_Intent.putExtra("SELECTED_POSITION", position + 1);
                startActivity(backToMain_Intent);
            }
        });
    }
}
