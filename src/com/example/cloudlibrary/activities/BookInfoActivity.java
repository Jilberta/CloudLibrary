package com.example.cloudlibrary.activities;

import com.example.cloudlibrary.activities.R;
import com.example.cloudlibrary.model.Book;
import com.example.cloudlibrary.qrcode.Contents;
import com.example.cloudlibrary.qrcode.QRCodeEncoder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookInfoActivity extends Activity {

	private Book b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_info);

		b = (Book) getIntent().getSerializableExtra("book");

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
		case R.id.qrCode:
			generateCRCode();
			break;
		default:
			break;
		}
	}

	private void generateCRCode() {

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

		} catch (WriterException e) {
			e.printStackTrace();
		}

	}

}
