package com.example.cloudlibrary.activities;

import com.example.cloudlibrary.activities.R;
import com.example.cloudlibrary.model.Book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
	
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download:
               Toast.makeText(this, "download", Toast.LENGTH_SHORT).show();
               break;
            default:
                break;
        }
    }


}
