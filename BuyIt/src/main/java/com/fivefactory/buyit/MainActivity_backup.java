package com.fivefactory.buyit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Coded by Sotti on 27/08/13.
 * Nobody creates nothing.
 * Just for fun.
 */

public class MainActivity_backup extends Activity implements FragmentManager.OnBackStackChangedListener
{
    private boolean inLocationAll, inLocalMC, mShowingLocationBack, mShowingLocalBack;
    private String[] mLocalList,mAppsList, mLocalFilters, mLocationsArray;
    private static int fragmentTag = R.layout.location_el_prat_local_all;
    private ListView mDrawerLocals, mDrawerApps;
    private LinearLayout mLeftDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle, mTitle;
    private DrawerLayout mDrawerLayout;
    private ActionBar actionBar;
    private int currentFilter = 0;
    private LinearLayout linearLayout;

    private ImageView mDrawerHeaderImage;
    private TextView mDrawerHeaderText;

    private static final int offer_Cards_Number = 4;

    private boolean offer_card_back[] = new boolean[offer_Cards_Number];



    // A handler object, used for deferring UI operations.
    private Handler mHandler = new Handler();
    // Whether or not we're showing the back of the card (otherwise showing the front).

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout)findViewById(R.id.left_drawer_view);

        initActionBar();
        initDrawer();
        initView(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState (Bundle outState)
    {
        outState.putBoolean("inLocationAll", inLocationAll);
        outState.putBoolean("inMc", inLocalMC);
        outState.putBoolean("mShowingLocationBack", mShowingLocationBack);
        outState.putBoolean("mShowingLocalBack", mShowingLocalBack);
        outState.putInt("currentFilter", currentFilter);
        outState.putBooleanArray("mBooleanArray", offer_card_back);

        super.onSaveInstanceState(outState);
    }


    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    private void inLocationAll()
    {
        inLocationAll = true;
        inLocalMC = false;

        if (mShowingLocationBack)
        {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            setTitle("El Prat Airport");
            actionBar.setDisplayShowTitleEnabled(true);
        }
        else
        {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            setTitle(mTitle);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void inLocalMC()
    {
        inLocationAll = false;
        inLocalMC = true;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        setTitle(mTitle);
        actionBar.setDisplayShowTitleEnabled(true);
    }


    private void initActionBar()
    {
        actionBar = getActionBar();
        mTitle = mDrawerTitle = getTitle();
        inLocationAll();

        //Spinner Adapter
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.local_filters, android.R.layout.simple_spinner_dropdown_item);

        ActionBar.OnNavigationListener mNavigationCallback = new ActionBar.OnNavigationListener()
        {
            // Get the same strings provided for the drop-down's ArrayAdapter
            //String[] strings = getResources().getStringArray(R.array.local_filters);

            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId)
            {
                if (currentFilter == itemPosition)
                {
                    return false;
                }

                currentFilter = itemPosition;

                switch (itemPosition)
                {
                    case 0: selectItem(100);
                        break;

                    case 1: selectItem(101);
                        break;

                    case 2: selectItem(102);
                        break;

                    case 3: selectItem(103);
                        break;
                }
                return false;
            }
        };

        actionBar.setListNavigationCallbacks(mSpinnerAdapter, mNavigationCallback);
    }


    private void initDrawer()
    {
        LayoutInflater inflater = getLayoutInflater();
        //ViewGroup mTop = (ViewGroup)inflater.inflate(R.layout.drawer_left_header_item, mDrawerLocals, false);

        mLocationsArray = getResources().getStringArray(R.array.locations_array);
        mLocalList = getResources().getStringArray(R.array.locals_array);
        mLocalFilters = getResources().getStringArray(R.array.local_filters);
        mAppsList = getResources().getStringArray(R.array.apps_array);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLocals = (ListView) findViewById(R.id.left_drawer);
        mLeftDrawer = (LinearLayout)findViewById(R.id.left_drawer_view);

        mDrawerHeaderImage = (ImageView)findViewById(R.id.drawer_header_image);
        mDrawerHeaderText = (TextView)findViewById(R.id.locationi_drawer_text_banner);

        TypedArray mLocalIcons = getResources().obtainTypedArray(R.array.left_drawer_icons);

        //mDrawerApps = (ListView) findViewById(R.id.right_drawer);

        //mDrawerLocals.addHeaderView(mTop);

        // Set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Set both adapters for the list views
        //mDrawerLocals.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_left_item, mLocalList));
        mDrawerLocals.setAdapter(new LeftDrawerItemAdapter(this, R.layout.left_drawer_row, mLocalList, mLocalIcons));
        //mDrawerApps.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_right_item, mAppsList));

        // Set the list's click listener
        mDrawerLocals.setOnItemClickListener(new DrawerItemClickListener());
        //mDrawerApps.setOnItemClickListener(new DrawerFilterClickListener());

        mDrawerToggle = new ActionBarDrawerToggle
                (
                        this,                  /* host Activity */
                        mDrawerLayout,         /* DrawerLayout object */
                        R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                        R.string.drawer_open,  /* "open drawer" description */
                        R.string.drawer_close  /* "close drawer" description */
                )
        {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view)
            {
                if (inLocationAll)
                {
                    inLocationAll();
                }

                if (inLocalMC)
                {
                    inLocalMC();
                }

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView)
            {
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setTitle(mDrawerTitle);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    private void initView(Bundle savedInstanceState)
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            int position = extras.getInt("SELECTED_POSITION");
            selectItem(position);
        }
        else
        {
            if (savedInstanceState == null)
            {
                inLocationAll();
                mShowingLocalBack = mShowingLocationBack = false;
                getFragmentManager().beginTransaction().add(R.id.content_frame, new FlipCardFragment().newInstance(R.layout.location_el_prat_local_all)).commit();

                getFragmentManager().beginTransaction().add(R.id.offer_prat_all_1, new FlipCardFragment().newInstance(R.layout.offer_prat_all_1_image)).commit();
                getFragmentManager().beginTransaction().add(R.id.offer_prat_all_2, new FlipCardFragment().newInstance(R.layout.offer_prat_all_2_image)).commit();
                getFragmentManager().beginTransaction().add(R.id.offer_prat_all_3, new FlipCardFragment().newInstance(R.layout.offer_prat_all_3_image)).commit();
                getFragmentManager().beginTransaction().add(R.id.offer_prat_all_4, new FlipCardFragment().newInstance(R.layout.offer_prat_all_4_image)).commit();

                Arrays.fill(offer_card_back, Boolean.FALSE);
            }
            else
            {
                inLocationAll = savedInstanceState.getBoolean("inLocationAll");
                inLocalMC = savedInstanceState.getBoolean("inMc");
                mShowingLocationBack = savedInstanceState.getBoolean("mShowingLocationBack");
                mShowingLocalBack = savedInstanceState.getBoolean("mShowingLocalBack");
                currentFilter = savedInstanceState.getInt("currentFilter") *  -1;
                offer_card_back = savedInstanceState.getBooleanArray("mBooleanArray");

                if (inLocationAll) inLocationAll();
                if (inLocalMC) inLocalMC();
            }

            // Monitor back stack changes to ensure the action bar shows the appropriate button (either "photo" or "info").
            getFragmentManager().addOnBackStackChangedListener(this);
        }
    }

    public void launch_offer_flipCard(View view)
    {
        switch (view.getId())
        {
            case R.id.offer_card_1_flip_button:
                offer_card_flip(0);
                break;

            case R.id.offer_card_2_flip_button:
                offer_card_flip(1);
                break;

            case R.id.offer_card_3_flip_button:
                offer_card_flip(2);
                break;

            case R.id.offer_card_4_flip_button:
                offer_card_flip(3);
                break;
        }
    }

    private void offer_card_flip(int card_id)
    {
        switch (card_id)
        {
            case 0:
                if (offer_card_back[card_id])
                {
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.offer_card_flip_right_in, R.animator.offer_card_flip_right_out, R.animator.offer_card_flip_left_in, R.animator.offer_card_flip_left_out).replace(R.id.offer_prat_all_1, new FlipCardFragment().newInstance(R.layout.offer_prat_all_1_image)).addToBackStack(null).commit();
                }
                else
                {
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.offer_card_flip_right_in, R.animator.offer_card_flip_right_out, R.animator.offer_card_flip_left_in, R.animator.offer_card_flip_left_out).replace(R.id.offer_prat_all_1, new FlipCardFragment().newInstance(R.layout.offer_prat_all_1_info)).addToBackStack(null).commit();
                }

                break;

            case 1:
                if (offer_card_back[card_id])
                {
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.offer_card_flip_right_in, R.animator.offer_card_flip_right_out, R.animator.offer_card_flip_left_in, R.animator.offer_card_flip_left_out).replace(R.id.offer_prat_all_2, new FlipCardFragment().newInstance(R.layout.offer_prat_all_2_image)).addToBackStack(null).commit();
                }
                else
                {
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.offer_card_flip_right_in, R.animator.offer_card_flip_right_out, R.animator.offer_card_flip_left_in, R.animator.offer_card_flip_left_out).replace(R.id.offer_prat_all_2, new FlipCardFragment().newInstance(R.layout.offer_prat_all_2_info)).addToBackStack(null).commit();
                }

                break;

            case 2:
                if (offer_card_back[card_id])
                {
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.offer_card_flip_right_in, R.animator.offer_card_flip_right_out, R.animator.offer_card_flip_left_in, R.animator.offer_card_flip_left_out).replace(R.id.offer_prat_all_3, new FlipCardFragment().newInstance(R.layout.offer_prat_all_3_image)).addToBackStack(null).commit();
                }
                else
                {
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.offer_card_flip_right_in, R.animator.offer_card_flip_right_out, R.animator.offer_card_flip_left_in, R.animator.offer_card_flip_left_out).replace(R.id.offer_prat_all_3, new FlipCardFragment().newInstance(R.layout.offer_prat_all_3_info)).addToBackStack(null).commit();
                }

                break;

            case 3:
                if (offer_card_back[card_id])
                {
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.offer_card_flip_right_in, R.animator.offer_card_flip_right_out, R.animator.offer_card_flip_left_in, R.animator.offer_card_flip_left_out).replace(R.id.offer_prat_all_4, new FlipCardFragment().newInstance(R.layout.offer_prat_all_4_image)).addToBackStack(null).commit();
                }
                else
                {
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.offer_card_flip_right_in, R.animator.offer_card_flip_right_out, R.animator.offer_card_flip_left_in, R.animator.offer_card_flip_left_out).replace(R.id.offer_prat_all_4, new FlipCardFragment().newInstance(R.layout.offer_prat_all_4_info)).addToBackStack(null).commit();
                }

                break;
        }

        offer_card_back[card_id] = !offer_card_back[card_id];
    }


    private void flipCard_unified()
    {
        if (inLocationAll)
        {
            if (mShowingLocationBack)
            {
                getFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in, R.animator.card_flip_left_out).replace(R.id.content_frame, new FlipCardFragment().newInstance(R.layout.location_el_prat_local_all)).addToBackStack(null).commit();

                getFragmentManager().beginTransaction().add(R.id.offer_prat_all_1, new FlipCardFragment().newInstance(R.layout.offer_prat_all_1_image)).commit();
                getFragmentManager().beginTransaction().add(R.id.offer_prat_all_2, new FlipCardFragment().newInstance(R.layout.offer_prat_all_2_image)).commit();
                getFragmentManager().beginTransaction().add(R.id.offer_prat_all_3, new FlipCardFragment().newInstance(R.layout.offer_prat_all_3_image)).commit();
                getFragmentManager().beginTransaction().add(R.id.offer_prat_all_4, new FlipCardFragment().newInstance(R.layout.offer_prat_all_4_image)).commit();

                Arrays.fill(offer_card_back, Boolean.FALSE);
            }
            else
            {
                // Create and commit a new fragment transaction that adds the fragment for the back of the card, uses custom animations, and is part of the fragment manager's back stack.
                // Replace the default fragment animations with animator resources representing rotations when switching to the back of the card, as well as animator
                // resources representing rotations when flipping back to the front (e.g. when the system Back button is pressed).
                // Replace any fragments currently in the container view with a fragment representing the next page (indicated by the just-incremented currentPage variable).
                // Add this transaction to the back stack, allowing users to press Back to get to the front of the card.
                // Commit the transaction.

                //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                //getFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in, R.animator.card_flip_left_out).replace(R.id.content_frame, new CardBackFragment()).addToBackStack(null).commit();
                getFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in, R.animator.card_flip_left_out).replace(R.id.content_frame, new FlipCardFragment().newInstance(R.layout.location_information_el_prat)).addToBackStack(null).commit();
            }
            mShowingLocationBack = !mShowingLocationBack;
            inLocationAll();
        }
        else
        {
            if(inLocalMC)
            {
                if (mShowingLocalBack)
                {
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in, R.animator.card_flip_left_out).replace(R.id.content_frame, new FlipCardFragment().newInstance(R.layout.location_el_prat_local_mcdonalds)).addToBackStack(null).commit();
                }
                else
                {
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in, R.animator.card_flip_left_out).replace(R.id.content_frame, new FlipCardFragment().newInstance(R.layout.location_information_mcdonalds)).addToBackStack(null).commit();
                }

                mShowingLocalBack = !mShowingLocalBack;
                inLocalMC();
            }
            else
            {
                Toast.makeText(this,"Not yet",Toast.LENGTH_SHORT).show();
            }
        }

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


    @Override
    public void onBackStackChanged()
    {
        // When the back stack changes, invalidate the options menu (action bar).
        invalidateOptionsMenu();
    }


    @Override
    public void setTitle(CharSequence title)
    {
        mTitle = title;
        actionBar.setTitle(mTitle);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /*private class DrawerFilterClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            launchApps(position);
        }
    }*/


    /*private void launchApps(int position)
    {
        //Here will be implemented the local filter actions
        switch (position)
        {
            case 0:
                Intent where_is_my_car_intent = new Intent(MainActivity.this, WhereIsMyCar.class);
                startActivity(where_is_my_car_intent);
                mDrawerApps.setItemChecked(position, true);
                mDrawerLayout.closeDrawer(mDrawerApps);
                break;

            case 1:
                Intent flyInfo_intent = new Intent(MainActivity.this, FlyInfo.class);
                startActivity(flyInfo_intent);
                mDrawerApps.setItemChecked(position, true);
                mDrawerLayout.closeDrawer(mDrawerApps);
                break;
        }
    }*/


    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            selectItem(position);
        }
    }


    private void selectItem(int position)
    {
        // update the main content by replacing fragments
        Fragment fragment = new LocalFragment();
        Bundle args = new Bundle();
        args.putInt(LocalFragment.ARG_LOCAL_NUMBER, position);
        args.putString(LocalFragment.ARG_LOCATION_NAME, mLocationsArray[0] );

        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerLocals.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(this.linearLayout);

        position +=1;

        if (position > 1 && position < 35)
        {
            mTitle = mLocalList[position];
            inLocalMC();
        }
        else
        {
            if (position == 100 || position == 101 || position == 102 || position == 103)
            {
                mTitle = mLocalFilters[position - 100];
            }
        }

        if (position == 2)
        {
            mDrawerHeaderImage.setImageResource(R.drawable.local_image_mcdonalds);
            mDrawerHeaderText.setText("McDonalds");
            mShowingLocalBack = false;
            inLocalMC();
        }
        else
        {
            if (position == 1 || position == 100)
            {
                mDrawerHeaderImage.setImageResource(R.drawable.el_prat_t1);
                mDrawerHeaderText.setText("El Pratt");
                mShowingLocationBack = false;
                inLocationAll();
            }
        }
    }


    public static class LocalFragment extends Fragment
    {
        public static final String ARG_LOCAL_NUMBER = "local_number";
        public static final String ARG_LOCATION_NAME ="location_number";

        public LocalFragment()
        {
            //Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            int local_number = getArguments().getInt(ARG_LOCAL_NUMBER);
            String local_name = getArguments().getString(ARG_LOCATION_NAME);


            View rootView = inflater.inflate(fragmentTag, container, false);

            switch (local_number + 1)
            {
                case 0:
                    Intent launch_location_choice = new Intent(container.getContext(), ChooseLocation.class);
                    startActivity(launch_location_choice);
                    break;

                case 1:
                    rootView = inflater.inflate(R.layout.location_el_prat_local_all, container, false);
                    fragmentTag = R.layout.location_el_prat_local_all;
                    getFragmentManager().beginTransaction().add(R.id.offer_prat_all_1, new FlipCardFragment().newInstance(R.layout.offer_prat_all_1_image)).commit();
                    getFragmentManager().beginTransaction().add(R.id.offer_prat_all_2, new FlipCardFragment().newInstance(R.layout.offer_prat_all_2_image)).commit();
                    getFragmentManager().beginTransaction().add(R.id.offer_prat_all_3, new FlipCardFragment().newInstance(R.layout.offer_prat_all_3_image)).commit();
                    getFragmentManager().beginTransaction().add(R.id.offer_prat_all_4, new FlipCardFragment().newInstance(R.layout.offer_prat_all_4_image)).commit();

                    break;

                case 2:
                    rootView = inflater.inflate(R.layout.location_el_prat_local_mcdonalds, container, false);
                    fragmentTag = R.layout.location_el_prat_local_mcdonalds;
                    break;

                case 3:
                    rootView = inflater.inflate(R.layout.location_el_prat_local_lizarran, container, false);
                    fragmentTag = R.layout.location_el_prat_local_lizarran;
                    break;

                case 4:
                    rootView = inflater.inflate(R.layout.location_el_prat_local_hagen_dazs, container, false);
                    fragmentTag = R.layout.location_el_prat_local_hagen_dazs;
                    break;

                case 5:
                    rootView = inflater.inflate(R.layout.location_el_prat_local_pans, container, false);
                    fragmentTag = R.layout.location_el_prat_local_pans;
                    break;

                case 101:
                    rootView = inflater.inflate(R.layout.location_el_prat_local_all, container, false);
                    fragmentTag = R.layout.location_el_prat_local_all;
                    getFragmentManager().beginTransaction().add(R.id.offer_prat_all_1, new FlipCardFragment().newInstance(R.layout.offer_prat_all_1_image)).commit();
                    getFragmentManager().beginTransaction().add(R.id.offer_prat_all_2, new FlipCardFragment().newInstance(R.layout.offer_prat_all_2_image)).commit();
                    getFragmentManager().beginTransaction().add(R.id.offer_prat_all_3, new FlipCardFragment().newInstance(R.layout.offer_prat_all_3_image)).commit();
                    getFragmentManager().beginTransaction().add(R.id.offer_prat_all_4, new FlipCardFragment().newInstance(R.layout.offer_prat_all_4_image)).commit();
                    break;

                case 102:
                    rootView = inflater.inflate(R.layout.location_el_prat_filter_restaurants, container, false);
                    fragmentTag = R.layout.location_el_prat_filter_restaurants;
                    break;

                case 103:
                    rootView = inflater.inflate(R.layout.location_el_prat_filter_coffe, container, false);
                    fragmentTag = R.layout.location_el_prat_filter_coffe;
                    break;

                case 104:
                    rootView = inflater.inflate(R.layout.location_el_prat_filter_shopping, container, false);
                    fragmentTag = R.layout.location_el_prat_filter_shopping;
                    break;

                default:
                    rootView = inflater.inflate(R.layout.location_el_prat_filter_no_offer, container, false);
                    fragmentTag = R.layout.location_el_prat_filter_no_offer;
                    break;
            }
            return rootView;
        }
    }

    public  void launch_choose_location(View view)
    {
        selectItem(-1);
    }

    public void launch_map_intent_prat(View view)
    {
        Intent maps_intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=el+prat+de+llobregat+airport"));
        startActivity(maps_intent);
    }

    public void launch_call_intent_prat(View view)
    {
        Intent call_intent = new Intent(Intent.ACTION_DIAL);
        call_intent.setData(Uri.parse("tel:"+ 902404702));
        startActivity(call_intent);
    }

    public void launch_web_intent_prat(View view)
    {
        Intent official_web_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.aena-aeropuertos.es/csee/Satellite/Aeropuerto-Barcelona/es/"));
        startActivity(official_web_intent);
    }

    public void launch_local_moreinfo_prat(View view)
    {
        String wikipedia_url = getResources().getString(R.string.wikipedia_url_pratt);
        Intent more_localinfo_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(wikipedia_url));
        startActivity(more_localinfo_intent);
    }

    public void launch_map_intent_mcdonalds(View view)
    {
        Intent maps_intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:41.290045,2.074042?z=19"));
        startActivity(maps_intent);
    }

    public void launch_call_intent_mcdonalds(View view)
    {
        Intent call_intent = new Intent(Intent.ACTION_DIAL);
        call_intent.setData(Uri.parse("tel:"+ 915664100));
        startActivity(call_intent);
    }

    public void launch_web_intent_mcdonalds(View view)
    {
        Intent official_web_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mcdonalds.com"));
        startActivity(official_web_intent);
    }

    public void launch_local_moreinfo_mcdonalds(View view)
    {
        String wikipedia_url = getResources().getString(R.string.wikipedia_url_mcdonalds);
        Intent more_localinfo_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(wikipedia_url));
        startActivity(more_localinfo_intent);
    }

    public void launch_buyit(View view)
    {
        Intent qr_example_intent = new Intent(MainActivity_backup.this, QR_Codes.class);
        startActivity(qr_example_intent);
    }


    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        // If the nav drawer is open, hide action items related to the content view
        boolean leftDrawerOpen = mDrawerLayout.isDrawerOpen(this.linearLayout);
        //boolean rightDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerApps);
        //boolean drawerOpen = (leftDrawerOpen||rightDrawerOpen);
        boolean drawerOpen = (leftDrawerOpen);
        menu.findItem(R.id.search).setVisible(!drawerOpen);

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // Add either a "photo" or "finish" button to the action bar, depending on which page is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_flip, 1, mShowingLocationBack ? R.string.action_photo : R.string.action_info);

        item.setIcon(mShowingLocationBack ? R.drawable.collections_labels_light : R.drawable.ic_action_info);

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Pass the event to ActionBarDrawerToggle, if it returns true, then it has handled the app icon touch event
        //The action bar home/up action should open or close the drawer
        //ActionBarDrawerToggle will take car of this.
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        // Handle action buttons
        switch(item.getItemId())
        {
            case R.id.action_flip:
                flipCard_unified();
                break;

            case R.id.settings:
                Intent settings_intent = new Intent(MainActivity_backup.this,Settings_Menu.class);
                startActivity(settings_intent);
                break;

            case R.id.profile:
                Intent profile_intent = new Intent(MainActivity_backup.this,Profile.class);
                startActivity(profile_intent);
                break;

            case R.id.send_feedback:
                Intent  sendFeedback_intent = new Intent(MainActivity_backup.this, Settings_SendFeedback.class);
                startActivity(sendFeedback_intent);
                break;

            case R.id.about:
                Intent about_intent = new Intent(MainActivity_backup.this,About.class);
                startActivity(about_intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}