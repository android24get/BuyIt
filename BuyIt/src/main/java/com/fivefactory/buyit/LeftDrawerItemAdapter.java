package com.fivefactory.buyit;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Coded by Sotti on 26/09/13.
 * Nobody creates nothing.
 * Just for fun.
 */
public class LeftDrawerItemAdapter extends ArrayAdapter<String>
{
    private LayoutInflater mInflater;

    private String[] mStrings;
    private TypedArray mIcons;

    private int mViewResourceId;

    public LeftDrawerItemAdapter(Context ctx, int viewResourceId, String[] strings, TypedArray icons)
    {
        super(ctx, viewResourceId, strings);

        mInflater = (LayoutInflater)ctx.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        mStrings = strings;
        mIcons = icons;

        mViewResourceId = viewResourceId;
    }

    @Override
    public int getCount()
    {
        return mStrings.length;
    }

    @Override
    public String getItem(int position)
    {
        return mStrings[position];
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        ImageView iv = (ImageView)convertView.findViewById(R.id.left_drawer_element_icon);
        iv.setImageDrawable(mIcons.getDrawable(position));

        TextView tv = (TextView)convertView.findViewById(R.id.left_drawer_element_text);
        tv.setText(mStrings[position]);

        return convertView;
    }
}
