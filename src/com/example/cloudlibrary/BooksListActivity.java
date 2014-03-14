package com.example.cloudlibrary;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class BooksListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_list);
		final ListView listview = (ListView) findViewById(R.id.booksList);
		String[] values = new String[] { "Kacia-adamiani?!", "Vefxistyaosani", "Sibrdzne-Sicruisa", "ddsvc" };

		final CustomAdapter adapter = new CustomAdapter(this, values);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final String item = (String) arg0.getItemAtPosition(arg2);
				Toast.makeText(arg1.getContext(), item, Toast.LENGTH_SHORT).show();
				Intent i = new Intent(BooksListActivity.this, BookInfoActivity.class);
				//i.putExtra("", value)
				startActivity(i);

			}

		});
	}

}
