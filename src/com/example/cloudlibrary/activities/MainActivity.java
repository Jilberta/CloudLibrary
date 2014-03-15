package com.example.cloudlibrary.activities;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.Activity;
//import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
//import android.widget.Toast;
import com.example.cloudlibrary.controllers.GPSTracker;
import com.example.cloudlibrary.activities.R;
import com.example.cloudlibrary.net.SyncBookList;
import com.example.cloudlibrary.net.SyncCommentList;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GPSTracker gp = new GPSTracker(this);
        gp.getLocation();


        Session.openActiveSession(this, true, new Session.StatusCallback() {

            // callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception) {
              if (session.isOpened()) {

              	Request.newMeRequest(session, new Request.GraphUserCallback() {
      				
      				@Override
      				public void onCompleted(GraphUser user, Response response) {
      					if (user != null) {
      		                TextView welcome = (TextView) findViewById(R.id.welcome);
      		                welcome.setText("Hello " + user.getName() + "!");
      		              }
      				}
      			}).executeAsync();
              }
            }
          });
    }

    public void onClick(View v) {
//        Intent k;
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
