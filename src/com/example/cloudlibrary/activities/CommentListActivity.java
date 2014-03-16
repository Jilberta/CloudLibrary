package com.example.cloudlibrary.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cloudlibrary.adapters.CommentListViewAdapter;
import com.example.cloudlibrary.controllers.GPSTracker;
import com.example.cloudlibrary.helpers.HelperMethods;
import com.example.cloudlibrary.model.Comment;
import com.example.cloudlibrary.net.SyncBookListUpdate;
import com.example.cloudlibrary.net.SyncCommentListUpdate;
import com.example.cloudlibrary.net.UploadComment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jay on 3/15/14.
 */
public class CommentListActivity extends Activity {
    private Activity activity;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        activity = this;

        ArrayList<Comment> commentList = (ArrayList<Comment>) getIntent().getSerializableExtra("CommentList");

        updateProfileInfo(this);

        lv = (ListView) findViewById(R.id.comment_list);
        CommentListViewAdapter adapter = new CommentListViewAdapter(this, commentList);
        lv.setAdapter(adapter);

        Button upload = (Button) findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);
                String txt = String.valueOf(input.getText());
                if (!txt.isEmpty()) {
                    UploadComment uc = new UploadComment(activity, lv);
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

    private void updateProfileInfo(Context ctx){
        EditText input = (EditText) findViewById(R.id.input);
        ImageView pic = (ImageView) findViewById(R.id.prof_pic);
        TextView name = (TextView) findViewById(R.id.name);
        Button upload = (Button) findViewById(R.id.upload);
        FacebookUserInfo fb = new FacebookUserInfo(ctx);
        if(fb.isReady()){
            String profName = fb.getName();
            String userId = fb.getId();
            String picUrl = HelperMethods.getUserProfilePictureUrl(userId);
            System.out.println("ASd");
        }else{
            input.setEnabled(false);
            upload.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.other_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                GPSTracker gps = new GPSTracker(this);
                double longitude = 0, latitude = 0;
                if (gps.canGetLocation()) {
                    ProgressDialog progress = ProgressDialog.show(this, "", getString(R.string.please_wait));
                    SyncCommentListUpdate sync = new SyncCommentListUpdate(this, progress, lv);
                    longitude = gps.getLongitude();
                    latitude = gps.getLatitude();
                    sync.makeRequest(longitude, latitude);
                } else {
                    gps.showSettingsAlert();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
