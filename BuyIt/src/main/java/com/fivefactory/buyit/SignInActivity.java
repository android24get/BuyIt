package com.fivefactory.buyit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;

/**
 * Coded by Sotti on 2/09/13.
 * Nobody creates nothing.
 * Just for fun.
 */
public class SignInActivity extends Activity implements OnClickListener, PlusClient.ConnectionCallbacks, PlusClient.OnConnectionFailedListener, PlusClient.OnAccessRevokedListener
{
    private static final String TAG = "SignInActivity";
    private static final int REQUEST_CODE_SIGN_IN = 1;
    private static final int REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES = 2;

    private PlusClient mPlusClient;
    private Button mSignInButton;
    private View mSignOutButton;
    private View mRevokeAccessButton;
    private ConnectionResult mConnectionResult;

    private LinearLayout mSign_in_layout, mSign_out_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);

        //mPlusClient = new PlusClient.Builder(this, this, this).setVisibleActivities("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity").build();
        mPlusClient = new PlusClient.Builder(this, this, this).build();

        initResources();
    }

    private void initResources()
    {
        mSignInButton = (Button) findViewById(R.id.sign_in_button_google);
        mSignInButton.setOnClickListener(this);
        mSignOutButton = findViewById(R.id.sign_out_button);
        mSignOutButton.setOnClickListener(this);
        mRevokeAccessButton = findViewById(R.id.revoke_access_button);
        mRevokeAccessButton.setOnClickListener(this);
        mSign_in_layout = (LinearLayout)findViewById(R.id.sign_in_layout);
        mSign_out_layout = (LinearLayout)findViewById(R.id.sign_out_layout);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // Connects the client to Google Play services. This method returns immediately, and connects to the service in the background.
        // If the connection is successful, onConnected(Bundle) is called. On a failure, onConnectionFailed(ConnectionResult) is called.
        mPlusClient.connect();
    }

    @Override
    // This method is called when the mPlusClient.connect() is successful.
    public void onConnected(Bundle connectionHint)
    {
        String currentPersonName = mPlusClient.getCurrentPerson() != null ? mPlusClient.getCurrentPerson().getDisplayName() : getString(R.string.unknown_person);
        Toast.makeText(this, getResources().getString(R.string.signed_in_status,currentPersonName),Toast.LENGTH_LONG).show();
        Log.d(TAG, getResources().getString(R.string.signed_in_status,currentPersonName));

        updateButtons(true);
    }

    @Override
    // This method is called when the mPlusClient.connect() is failed.
    public void onConnectionFailed(ConnectionResult result)
    {
        mConnectionResult = result;
        updateButtons(false);
    }

    @Override
    protected void onStop()
    {
        mPlusClient.disconnect();
        super.onStop();
    }

    @Override
    // This method is called when the mPlusClient.disconnect() is successful.
    public void onDisconnected()
    {
        Log.d(TAG, getResources().getString(R.string.signed_out_status));
        Toast.makeText(this, getResources().getString(R.string.signed_out_status),Toast.LENGTH_LONG).show();

        Log.d(TAG, getResources().getString(R.string.loading_status));
        mPlusClient.connect();
        updateButtons(false);

        Log.d(TAG, "disconnected");
    }

    protected void onActivityResult (int requestCode, int responseCode, Intent data)
    {
        if (requestCode == REQUEST_CODE_SIGN_IN || requestCode == REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES)
        {
            if (responseCode == RESULT_OK && !mPlusClient.isConnected() && !mPlusClient.isConnecting())
            {
                // This time, connect should succeed.
                mPlusClient.connect();
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.sign_in_button_google:
                int available = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
                if (available != ConnectionResult.SUCCESS)
                {
                    return;
                }

                try
                {
                    Log.d(TAG, getResources().getString(R.string.signing_in_status));
                    mConnectionResult.startResolutionForResult(this, REQUEST_CODE_SIGN_IN);
                    this.finish();
                }
                catch (IntentSender.SendIntentException e)
                {
                    // Fetch a new result to start.
                    mPlusClient.connect();
                }
                break;

            case R.id.sign_out_button:
                if (mPlusClient.isConnected())
                {
                    mPlusClient.clearDefaultAccount();
                    mPlusClient.disconnect();
                    mPlusClient.connect();
                }
                break;

            case R.id.revoke_access_button:
                if (mPlusClient.isConnected())
                {
                    mPlusClient.revokeAccessAndDisconnect(this);
                    updateButtons(false);
                }
                break;
        }
    }

    @Override
    public void onAccessRevoked(ConnectionResult status)
    {
        if (status.isSuccess())
        {
            Toast.makeText(this, getResources().getString(R.string.revoke_access_status),Toast.LENGTH_LONG).show();
            Log.d(TAG, getResources().getString(R.string.revoke_access_status));
        }
        else
        {
            Toast.makeText(this, getResources().getString(R.string.revoke_access_error_status),Toast.LENGTH_LONG).show();
            Log.d(TAG, getResources().getString(R.string.revoke_access_error_status));
            mPlusClient.disconnect();
        }
        mPlusClient.connect();
    }

    private void updateButtons(boolean isSignedIn)
    {
        if (isSignedIn)
        {
            mSignInButton.setVisibility(View.INVISIBLE);
            mSignOutButton.setEnabled(true);
            mRevokeAccessButton.setEnabled(true);
        }
        else
        {
            if (mConnectionResult == null)
            {
                // Disable the sign-in button until onConnectionFailed is called with result.
                mSignInButton.setVisibility(View.INVISIBLE);
                Log.d(TAG, getResources().getString(R.string.loading_status));
            }
            else
            {
                // Enable the sign-in button since a connection result is available.
                mSignInButton.setVisibility(View.VISIBLE);
                //Log.d(TAG, getResources().getString(R.string.signed_out_status));
                //Toast.makeText(this, getResources().getString(R.string.signed_out_status),Toast.LENGTH_LONG).show();
            }

            mSignOutButton.setEnabled(false);
            mRevokeAccessButton.setEnabled(false);
        }
        setSignInLayout(isSignedIn);
    }

    private void setSignInLayout(boolean SignedIn)
    {
        if (SignedIn)
        {
            mSign_in_layout.setVisibility(View.INVISIBLE);
            mSign_out_layout.setVisibility(View.VISIBLE);
        }
        else
        {
            mSign_in_layout.setVisibility(View.VISIBLE);
            mSign_out_layout.setVisibility(View.INVISIBLE);
        }
    }
}