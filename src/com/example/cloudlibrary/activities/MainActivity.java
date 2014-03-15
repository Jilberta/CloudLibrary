package com.example.cloudlibrary.activities;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.cloudlibrary.controllers.GPSTracker;
import com.example.cloudlibrary.R;
import com.example.cloudlibrary.net.SyncBookList;
import com.example.cloudlibrary.net.SyncCommentList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

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

                    Geocoder gc = new Geocoder(this, new Locale("en"));
                    try {
                        ArrayList<Address> list = (ArrayList<Address>) gc.getFromLocation(longitude, latitude, 5);
                        for(int i = 0; i < list.size(); i++){
                            Address addr = list.get(i);
                            System.out.println("ASd");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SyncBookList sync = new SyncBookList(this);
                    sync.makeRequest(longitude, latitude);
                }else{
                    gps.showSettingsAlert();
                }
                break;
            case R.id.comments:
//                Toast.makeText(this, "comments", Toast.LENGTH_LONG).show();
                //k = new Intent(MainActivity.this, CommentsActivity.class);
                //startActivity(k);
                GPSTracker gps2 = new GPSTracker(this);
                if (gps2.canGetLocation()) {
                    double latitude = gps2.getLatitude();
                    double longitude = gps2.getLongitude();
//                    Toast.makeText(this, "Your Location is: \nLatitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_LONG).show();

                    SyncCommentList syncComment = new SyncCommentList(this);
                    syncComment.makeRequest(longitude, latitude);
                }else{
                    gps2.showSettingsAlert();
                }

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
