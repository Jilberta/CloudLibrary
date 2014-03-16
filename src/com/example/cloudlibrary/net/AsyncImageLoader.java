package com.example.cloudlibrary.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncImageLoader extends AsyncTask<String, Void, Bitmap> {
	private ImageView bookImage;
	
	public AsyncImageLoader(ImageView bookImage){
		this.bookImage = bookImage;
	}
	
	@Override
	protected Bitmap doInBackground(String... arg0) {
		Bitmap img = getBitmapFromURL(arg0[0]);
		return img;
	}
	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		bookImage.setImageBitmap(result);
	}

}
