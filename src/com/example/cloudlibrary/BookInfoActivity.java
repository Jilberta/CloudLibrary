package com.example.cloudlibrary;

import com.example.cloudlibrary.model.Book;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BookInfoActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_info);
		
		
		Book b = (Book) getIntent().getSerializableExtra("book");
		
		TextView bookAuthor = (TextView) findViewById(R.id.bookAuthor);
		TextView bookTitle = (TextView) findViewById(R.id.bookTitle);
		TextView bookDescr = (TextView) findViewById(R.id.reviewTxt);
		
		bookAuthor.setText(b.getAuthorInfo());
		bookTitle.setText(b.getTitle());
		bookDescr.setText(b.getBriefDescription());
	}

}
