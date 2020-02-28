package asu.ker.heatmappers.heatmappers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private CheckInFragment checkInFragment;
    private HeatFragment heatFragment;
    private HistoryFragment historyFragment;
    private SurveyFragment surveyFragment;
    private FusedLocationProviderClient myFusedLocationClient;
    private String mdropID;
    private boolean mScanning;
    private Handler mHandler;
    private String mDeviceName;
    private String mDeviceAddress;
    private boolean mConnected = false;
    private String mConnectionState;


    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkInFragment = new CheckInFragment();
        heatFragment = new HeatFragment();
        historyFragment = new HistoryFragment();
        surveyFragment = new SurveyFragment();
        mTextMessage = (TextView) findViewById(R.id.message);
        checkPermission();

        myFusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, checkInFragment);
        transaction.commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);


        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        mdropID = preferences.getString("dropID", null);
        mDeviceName = preferences.getString("mDeviceName", null);
        mConnectionState = "Disconnected";

        Log.w("asd","asdsd" + mdropID + mDeviceName + mDeviceAddress);
        mHandler = new Handler();



        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("Navigation item:",String.valueOf(item.getItemId()));
                Fragment selectedFragment = null;
               // mTextMessage.setVisibility(View.INVISIBLE);
                switch (item.getItemId()) {
                    case R.id.navigation_checkin:

                        Log.d("Checkin","Checkin clicked");
                        selectedFragment = checkInFragment;
                        break;
                    case R.id.navigation_heat:

                        Log.d("Heat","Heat picked");
                        selectedFragment = heatFragment;
                        break;
                    case R.id.navigation_history:

                        Log.d("History","history checked");
                        selectedFragment = historyFragment;
                        break;
                    case R.id.navigation_survey:

                        Log.d("Survey", "Survey taken");
                        selectedFragment = surveyFragment;
                        break;

                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                return true;

            }
        });
    }



}
