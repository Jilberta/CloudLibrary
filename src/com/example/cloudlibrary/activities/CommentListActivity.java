package com.example.cloudlibrary.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cloudlibrary.activities.R;
import com.example.cloudlibrary.adapters.CommentListViewAdapter;
import com.example.cloudlibrary.controllers.GPSTracker;
import com.example.cloudlibrary.model.Comment;
import com.example.cloudlibrary.net.UploadComment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jay on 3/15/14.
 */
public class CommentListActivity extends Activity {
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        activity = this;

        ArrayList<Comment> commentList = (ArrayList<Comment>) getIntent().getSerializableExtra("CommentList");

        ListView lv = (ListView) findViewById(R.id.comment_list);
        CommentListViewAdapter adapter = new CommentListViewAdapter(this, commentList);
        lv.setAdapter(adapter);

        Button upload = (Button) findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);
                String txt = String.valueOf(input.getText());
                if (!txt.isEmpty()) {
                    UploadComment uc = new UploadComment(activity);
                    SimpleDateFormat sf = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
                    String dat = sf.format(Calendar.getInstance().getTime());
                    GPSTracker gps = new GPSTracker(activity);
                    double longitude = 0, latitude = 0;
                    if (gps.canGetLocation()) {
                        longitude = gps.getLongitude();
                        latitude = gps.getLatitude();
                        uc.makeRequest("123", txt, dat, longitude, latitude);
                    } else {
                        gps.showSettingsAlert();
                    }
                }
            }
        });
    }


}