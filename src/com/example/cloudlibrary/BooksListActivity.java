package com.example.cloudlibrary;


import java.util.ArrayList;

import com.example.cloudlibrary.model.Book;

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
		ArrayList<Book> books = new ArrayList<Book>();
		
		
		Book b1 = new Book();
		Book b2 = new Book();
		Book b3 = new Book();
		Book b4 = new Book();
		b1.setTitle("Kacia-adamiani?!");
		b1.setAuthorInfo("Ilia Chavchavadze");
		b1.setBriefDescription("dahsihdash dhahdhdash asudasiyd djaosjdjdo dasojdaosjdasoij");
		b2.setTitle("Vefxistyaosani");
		b2.setAuthorInfo("Shota Rustaveli");
		b3.setTitle("Sibrdzne-Sicruisa");
		b3.setAuthorInfo("Sulxan-Saba Orbeliani");
		b4.setAuthorInfo("Kote, Kikola, Jilberta, Laki");
		b4.setTitle("Cloud Library");
		books.add(b1);
		books.add(b2);
		books.add(b3);
		books.add(b4);

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

}
