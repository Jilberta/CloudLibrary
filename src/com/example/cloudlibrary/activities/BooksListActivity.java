package com.example.cloudlibrary.activities;


import java.util.ArrayList;
import com.example.cloudlibrary.adapters.CustomAdapter;
import com.example.cloudlibrary.controllers.GPSTracker;
import com.example.cloudlibrary.model.Book;
import com.example.cloudlibrary.net.SyncBookListUpdate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class BooksListActivity extends Activity {
    private ListView listview;
    private GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_list);
		listview = (ListView) findViewById(R.id.booksList);

        ArrayList<Book> books = (ArrayList<Book>) getIntent().getSerializableExtra("BookList");

		final CustomAdapter adapter = new CustomAdapter(this, books);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final Book item = (Book) arg0.getItemAtPosition(arg2);
				Toast.makeText(arg1.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
				Intent i = new Intent(BooksListActivity.this, BookInfoActivity.class);
				i.putExtra("book", item);
				startActivity(i);
			}

		});
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.other_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                GPSTracker gps = new GPSTracker(this);
                double longitude = 0, latitude = 0;
                if (gps.canGetLocation()) {
                    ProgressDialog progress = ProgressDialog.show(this, "", getString(R.string.please_wait));
                    SyncBookListUpdate sync = new SyncBookListUpdate(this, progress, listview);
                    longitude = gps.getLongitude();
                    latitude = gps.getLatitude();
                    sync.makeRequest(longitude, latitude);
                } else {
                    gps.showSettingsAlert();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
