package com.example.cloudlibrary.adapters;

import java.util.ArrayList;

import com.example.cloudlibrary.activities.R;
import com.example.cloudlibrary.model.Book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Book> {
	private final Context context;
	private final ArrayList<Book> values;

	public CustomAdapter(Context context, ArrayList<Book> books) {
		super(context, R.layout.list_item, books);
		this.context = context;
		this.values = books;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.bookName);
		textView.setText(values.get(position).getTitle());

		return rowView;
	}
}