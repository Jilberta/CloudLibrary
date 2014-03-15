package com.example.cloudlibrary.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.cloudlibrary.controllers.GPSTracker;
import com.example.cloudlibrary.R;
import com.example.cloudlibrary.net.SyncBookList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View v) {
        Intent k;
        switch (v.getId()) {
            case R.id.books:
                GPSTracker gps = new GPSTracker(this);
                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
//                    Toast.makeText(this, "Your Location is: \nLatitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_LONG).show();

                    SyncBookList sync = new SyncBookList(this);
                    sync.makeRequest(longitude, latitude);
                }else{
                    gps.showSettingsAlert();
                }
                break;
            case R.id.comments:
                Toast.makeText(this, "comments", Toast.LENGTH_LONG).show();
                //k = new Intent(MainActivity.this, CommentsActivity.class);
                //startActivity(k);
                break;
            case R.id.test:

                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
