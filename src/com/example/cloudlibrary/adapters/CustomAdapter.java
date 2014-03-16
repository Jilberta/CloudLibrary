package com.example.cloudlibrary.adapters;

import java.util.ArrayList;
import com.example.cloudlibrary.activities.R;
import com.example.cloudlibrary.model.Book;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Book> {
	private Context context;
	private ArrayList<Book> values;

	public CustomAdapter(Context context, ArrayList<Book> books) {
		super(context, R.layout.list_item, books);
		this.context = context;
		this.values = books;
	}

    public void setNewValues(ArrayList<Book> bookList){
        this.values = bookList;
        notifyDataSetChanged();
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item, parent, false);

		TextView textView = (TextView) rowView.findViewById(R.id.bookName);
		textView.setText(values.get(position).getTitle());

        ImageView img = (ImageView) rowView.findViewById(R.id.img);
        Book book = values.get(position);
        if(book.getBitmap() != null)
            img.setImageBitmap(book.getBitmap());

		return rowView;
	}
}