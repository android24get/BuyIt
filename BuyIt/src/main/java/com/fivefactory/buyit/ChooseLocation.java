package com.fivefactory.buyit;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Coded by Sotti on 2/09/13.
 * Nobody creates nothing.
 * Just for fun.
 */
public class ChooseLocation extends Activity implements FragmentManager.OnBackStackChangedListener
{
    private static final int choose_Location_Cards_Number = 4;

    private boolean mShowingBack_Choose_Location_Cards[] = new boolean[choose_Location_Cards_Number];

    FragmentManager mFragmentManager = getFragmentManager();

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_location);

        getActionBar().setDisplayHomeAsUpEnabled(false);

        if (savedInstanceState == null)
        {
            mFragmentManager.beginTransaction().add(R.id.choose_location_card_image_1, new FlipCardFragment().newInstance(R.layout.location_image_el_prat)).commit();
            mFragmentManager.beginTransaction().add(R.id.choose_location_card_image_2, new FlipCardFragment().newInstance(R.layout.location_image_maremagnum)).commit();
            mFragmentManager.beginTransaction().add(R.id.choose_location_card_image_3, new FlipCardFragment().newInstance(R.layout.location_image_sants)).commit();
            mFragmentManager.beginTransaction().add(R.id.choose_location_card_image_4, new FlipCardFragment().newInstance(R.layout.location_image_wtc_bcn)).commit();

            Arrays.fill(mShowingBack_Choose_Location_Cards, Boolean.FALSE);
        }
        else
        {
            mShowingBack_Choose_Location_Cards = savedInstanceState.getBooleanArray("mBooleanArray");
        }
    }

    @Override
    public void onSaveInstanceState (Bundle outState)
    {
        outState.putBooleanArray("mBooleanArray", mShowingBack_Choose_Location_Cards);

        super.onSaveInstanceState(outState);
    }

    private void change(int change_id)
    {
        mShowingBack_Choose_Location_Cards[change_id] = !mShowingBack_Choose_Location_Cards[change_id];
    }

    public void launch_location(View view)
    {
        Class mActivity = MainActivity.class;
        switch (view.getId())
        {
            case R.id.choose_location_card_explore_1:
                mActivity = MainActivity.class;
                Intent launch_main_intent = new Intent (view.getContext(),mActivity);
                startActivity(launch_main_intent);
                break;

            case R.id.choose_location_card_explore_2:
                Toast.makeText(view.getContext(),"Not yet. Check soon!",Toast.LENGTH_SHORT).show();
                break;

            case R.id.choose_location_card_explore_3:
                Toast.makeText(view.getContext(),"Not yet. Check soon!",Toast.LENGTH_SHORT).show();
                break;

            case R.id.choose_location_card_explore_4:
                Toast.makeText(view.getContext(),"Not yet. Check soon!",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void launch_choose_location_flipCard(View view)
    {
        switch (((View) view.getParent().getParent()).getId())
        {
            case R.id.choose_location_card_explore_1:
                flipCard(0);
                break;

            case R.id.choose_location_card_explore_2:
                flipCard(1);
                break;

            case R.id.choose_location_card_explore_3:
                flipCard(2);
                break;

            case R.id.choose_location_card_explore_4:
                flipCard(3);
                break;

        }

        switch (view.getId())
        {
            case R.id.flip_button_1:
                flipCard(0);
                break;

            case R.id.flip_button_2:
                flipCard(1);
                break;

            case R.id.flip_button_3:
                flipCard(2);
                break;

            case R.id.flip_button_4:
                flipCard(3);
                break;
        }
    }

    private void flipCard(int i)
    {
        int flipper = 0;
        int flipped = 0;

        switch (i)
        {
            case 0:
                flipper = R.id.choose_location_card_image_1;
                flipped = R.layout.location_map_el_prat;

                if (mShowingBack_Choose_Location_Cards[0])
                {
                    flipped =R.layout.location_image_el_prat;
                }
                break;

            case 1:
                flipper = R.id.choose_location_card_image_2;
                flipped = R.layout.location_map_maremagnum;

                if (mShowingBack_Choose_Location_Cards[1])
                {
                    flipped = R.layout.location_image_maremagnum;
                }
                break;

            case 2:
                flipper = R.id.choose_location_card_image_3;
                flipped = R.layout.location_map_sants;

                if (mShowingBack_Choose_Location_Cards[2])
                {
                    flipped = R.layout.location_image_sants;
                }
                break;

            case 3:
                flipper = R.id.choose_location_card_image_4;
                flipped = R.layout.location_map_wtc_bcn;

                if (mShowingBack_Choose_Location_Cards[3])
                {
                    flipped = R.layout.location_image_wtc_bcn;
                }
                break;
        }

        change(i);

        // Create and commit a new fragment transaction that adds the fragment for the back of the card, uses custom animations, and is part of the fragment manager's back stack.
        // Replace the default fragment animations with animator resources representing rotations when switching to the back of the card, as well as animator resources representing rotations when flipping back to the front (e.g. when the system Back button is pressed).
        // Replace any fragments currently in the container view with a fragment representing the next page (indicated by the just-incremented currentPage variable).
        // Add this transaction to the back stack, allowing users to press Back to get to the front of the card.
        // Commit the transaction.

        mFragmentManager.beginTransaction().setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in, R.animator.card_flip_left_out).replace(flipper, new FlipCardFragment().newInstance(flipped)).addToBackStack(null).commit();

        // Defer an invalidation of the options menu (on modern devices, the action bar). This can't be done immediately because the transaction may not yet be committed. Commits are asynchronous in that they are posted to the main thread's message loop.
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public void onBackStackChanged()
    {

    }


    // A fragment representing the states of the card.
    public static class FlipCardFragment extends Fragment
    {
        public FlipCardFragment()
        {

        }

        public static FlipCardFragment newInstance (int resource_id)
        {
            Log.d("CardBackFragment", "Putting " + resource_id + " into newInstance");
            FlipCardFragment f = new FlipCardFragment();
            Bundle args = new Bundle();
            args.putInt("RESOURCE_ID", resource_id);
            f.setArguments(args);
            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            int resource_id = 0;

            Bundle args = getArguments();
            if (args != null)
            {
                resource_id = args.getInt("RESOURCE_ID");
                Log.d("CardFrontFragment", "found resource_id of " + String.valueOf(resource_id));
            }
            return inflater.inflate(resource_id, container, false);
        }
    }
}