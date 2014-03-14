package com.example.cloudlibrary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

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
			Toast.makeText(this, "books", Toast.LENGTH_LONG).show();
			k = new Intent(MainActivity.this, BooksListActivity.class);
			startActivity(k);
			break;
		case R.id.comments:
			Toast.makeText(this, "comments", Toast.LENGTH_LONG).show();
			//k = new Intent(MainActivity.this, CommentsActivity.class);
			//startActivity(k);
			break;
		case R.id.test:
			Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
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
