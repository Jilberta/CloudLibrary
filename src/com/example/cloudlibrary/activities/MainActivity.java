package com.example.cloudlibrary.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.cloudlibrary.controllers.GPSTracker;
import com.example.cloudlibrary.net.SyncBookList;
import com.example.cloudlibrary.net.SyncCommentList;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

//import android.content.Intent;

public class MainActivity extends FragmentActivity {

	private SharedPreferences prefs;

	private ProgressDialog progress;

	private GPSTracker gps;

	private MainFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		

		if (savedInstanceState == null) {
			// Add the fragment on initial activity setup
			mainFragment = new MainFragment();
			getSupportFragmentManager().beginTransaction()
					.add(android.R.id.content, mainFragment).commit();
			Session.openActiveSession(this, true, new Session.StatusCallback() {
				// callback when session changes state
				@Override
				public void call(Session session, SessionState state,
						Exception exception) {
					if (session.isOpened()) {

						Request.newMeRequest(session,
								new Request.GraphUserCallback() {

									@Override
									public void onCompleted(GraphUser user,
											Response response) {
										if (user != null) {
											TextView welcome = (TextView) findViewById(R.id.welcome);
											welcome.setText("username = "
													+ user.getUsername()
													+ "; id = " + user.getId());
											System.out.println(user
													.getUsername()
													+ " id -> "
													+ user.getId());
											Editor editor = prefs.edit();
											// editor.putString("username",
											// user.getUsername());
											editor.putString("name",
													user.getName());
											editor.putString("id", user.getId());
											editor.commit();
										}
									}

								}).executeAsync();

					}
				}
			});
			

		} else {
			// Or set the fragment from restored state info
			mainFragment = (MainFragment) getSupportFragmentManager()
					.findFragmentById(android.R.id.content);
		}

		gps = new GPSTracker(this);

		prefs = getSharedPreferences("pref", 0);
	}

	private String userName = null;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.books:
			progress = ProgressDialog.show(this, "",
					getString(R.string.please_wait));
			// GPSTracker gps = new GPSTracker(this);
			if (gps.canGetLocation()) {
				double latitude = gps.getLatitude();
				double longitude = gps.getLongitude();
				SyncBookList sync = new SyncBookList(this, progress);
				sync.makeRequest(longitude, latitude);
			} else {
				gps.showSettingsAlert();
			}
			break;
		case R.id.comments:
			progress = ProgressDialog.show(this, "",
					getString(R.string.please_wait));
			// GPSTracker gps2 = new GPSTracker(this);
			if (gps.canGetLocation()) {
				double latitude = gps.getLatitude();
				double longitude = gps.getLongitude();
				SyncCommentList syncComment = new SyncCommentList(this,
						progress);
				syncComment.makeRequest(longitude, latitude);
			} else {
				gps.showSettingsAlert();
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
