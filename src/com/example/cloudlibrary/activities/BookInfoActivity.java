package com.example.cloudlibrary.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import com.example.cloudlibrary.helpers.ServiceAddresses;
import com.example.cloudlibrary.model.Book;
import com.example.cloudlibrary.qrcode.Contents;
import com.example.cloudlibrary.qrcode.QRCodeEncoder;
import com.example.cloudlibrary.volley.RequestQueue;
import com.example.cloudlibrary.volley.toolbox.ImageLoader;
import com.example.cloudlibrary.volley.toolbox.NetworkImageView;
import com.example.cloudlibrary.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.LruCache;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookInfoActivity extends Activity {

	private Book b;
	private DownloadManager downloadmanager;
	private long reference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_info);

		b = (Book) getIntent().getSerializableExtra("book");

//        ImageView bookImage = (ImageView) findViewById(R.id.bookImage);
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
			downloadBook();
			Toast.makeText(this, "download", Toast.LENGTH_SHORT).show();
			break;
		case R.id.qrCode:
			Bitmap img = getQRCodeImage();
			createImagefileAndSave(img);
			break;
		default:
			break;
		}
	}

	private void createImagefileAndSave(Bitmap img) {
		File myFolder = new File(Environment.getExternalStorageDirectory() + "/CloudLibrary/QRCodes");
		if (!myFolder.exists()) {
			myFolder.mkdirs();
		}
		String url = b.getDownloadUrl();
		String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
		String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
		File imgFile = new File(Environment.getExternalStorageDirectory() + "/CloudLibrary/QRCodes/" +  fileNameWithoutExtn + ".png");
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(imgFile);
			img.compress(Bitmap.CompressFormat.PNG, 90, out);
			Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Throwable ignore) {
			}
		}

	}

	BroadcastReceiver onComplete = new BroadcastReceiver() {
		public void onReceive(Context ctxt, Intent intent) {
			Uri url = downloadmanager.getUriForDownloadedFile(reference);
			openFile(url.getPath());
			unregisterReceiver(onComplete);
		}
	};

	protected void openFile(String fileName) {
		Intent target = new Intent(Intent.ACTION_VIEW);
		target.setDataAndType(Uri.fromFile(new File(fileName)), "application/pdf");
		startActivity(target);

	}

	private void downloadBook() {
		String servicestring = Context.DOWNLOAD_SERVICE;
		downloadmanager = (DownloadManager) getSystemService(servicestring);
		String url = b.getDownloadUrl();
		Uri uri = Uri.parse(ServiceAddresses.IP + b.getDownloadUrl());
		DownloadManager.Request request = new Request(uri);
		String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());

		
		File myFolder = new File(Environment.getExternalStorageDirectory() + "/CloudLibrary/Books");

		if (!myFolder.exists()) {
			myFolder.mkdirs();
		}
		reference = downloadmanager.enqueue(request.setAllowedNetworkTypes(
				DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
				.setAllowedOverRoaming(false).setTitle(fileName)
				.setDescription("File Download")
				.setDestinationInExternalPublicDir("CloudLibrary/Books", fileName));
		registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

	}

	private Bitmap getQRCodeImage() {
		// Find screen size
		WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		int width = point.x;
		int height = point.y;
		int smallerDimension = width < height ? width : height;
		smallerDimension = smallerDimension * 3 / 4;

		// Encode with a QR Code image
		QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(b.getDownloadUrl(), null, Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), smallerDimension);
		try {
			Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
			ImageView myImage = (ImageView) findViewById(R.id.imageView1);

			myImage.setImageBitmap(bitmap);
			return bitmap;

		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}
}
