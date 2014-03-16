package com.example.cloudlibrary.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import com.example.cloudlibrary.adapters.CustomAdapter;
import com.example.cloudlibrary.controllers.GPSTracker;
import com.example.cloudlibrary.helpers.HelperMethods;
import com.example.cloudlibrary.helpers.ServiceAddresses;
import com.example.cloudlibrary.model.Book;
import com.example.cloudlibrary.model.Comment;
import com.example.cloudlibrary.net.SyncBookListUpdate;
import com.example.cloudlibrary.net.SyncCommentListUpdate;
import com.example.cloudlibrary.net.UploadComment;
import com.example.cloudlibrary.volley.Request;
import com.example.cloudlibrary.volley.RequestQueue;
import com.example.cloudlibrary.volley.Response;
import com.example.cloudlibrary.volley.VolleyError;
import com.example.cloudlibrary.volley.toolbox.ImageRequest;
import com.example.cloudlibrary.volley.toolbox.JsonObjectRequest;
import com.example.cloudlibrary.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jay on 3/15/14.
 */
public class CommentListActivity extends Activity {
    private Activity activity;
    private ListView lv;
    private Comment myComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        activity = this;

        ArrayList<Comment> commentList = (ArrayList<Comment>) getIntent().getSerializableExtra("CommentList");

        String id = HelperMethods.lashasId;
        String n = HelperMethods.lashaName;
        String k3 = HelperMethods.getUserProfilePictureUrl(id);
        myComment = new Comment(id, n, null, null, null, null);
        RequestQueue q = Volley.newRequestQueue(this);
        Resp3 rsp3 = new Resp3(myComment, this);
        ImageRequest request3 = new ImageRequest(k3, rsp3, 0, 0, null, new com.example.cloudlibrary.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ASd");
            }
        });
        q.add(request3);

//        myComment = (Comment) getIntent().getSerializableExtra("MyComment");

//        updateProfileInfo(this, myComment);


        lv = (ListView) findViewById(R.id.comment_list);
        CommentListViewAdapter adapter = new CommentListViewAdapter(this, commentList);
        lv.setAdapter(adapter);

        for(int i = 0; i < commentList.size(); i++){
            Comment comment = commentList.get(i);
            String userId = comment.getId();
            String url = ServiceAddresses.FACEBOOK_URL + comment.getId();
            RequestQueue queue = Volley.newRequestQueue(this);
            Resp2 rsp = new Resp2(comment, adapter);
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, rsp, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println();
                }
            });
            queue.add(req);
        }


        for(int i = 0; i < commentList.size(); i++){
            Comment comment = commentList.get(i);
            String k = HelperMethods.getUserProfilePictureUrl(comment.getId());
            RequestQueue queue = Volley.newRequestQueue(this);
            Resp rsp = new Resp(comment, adapter);
            ImageRequest request = new ImageRequest(k, rsp, 0, 0, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("ASd");
                }
            });
            queue.add(request);
        }


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
                        uc.makeRequest(myComment.getId(), txt, dat, longitude, latitude);
                    } else {
                        gps.showSettingsAlert();
                    }
                }
            }
        });
    }

    private class Resp implements Response.Listener<Bitmap>{
        private Comment comment;
        private CommentListViewAdapter adapter;

        public Resp(Comment comment, CommentListViewAdapter adapter){
            this.comment = comment;
            this.adapter = adapter;
        }

        @Override
        public void onResponse(Bitmap response) {
            comment.setBitmap(response);
            adapter.notifyDataSetChanged();
        }
    }

    private class Resp2 implements Response.Listener<JSONObject>{
        private Comment comment;
        private CommentListViewAdapter adapter;

        public Resp2(Comment comment, CommentListViewAdapter adapter){
            this.comment = comment;
            this.adapter = adapter;
        }

        @Override
        public void onResponse(JSONObject response) {
            try {
                String name = response.getString("name");
                String imageUrl = HelperMethods.getUserProfilePictureUrl(comment.getId());
                comment.setName(name);
                comment.setImageUrl(imageUrl);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    }

    private class Resp3 implements com.example.cloudlibrary.volley.Response.Listener<Bitmap>{
        private Comment comment;
        private Context ctx;

        public Resp3(Comment comment, Context ctx){
            this.comment = comment;
            this.ctx = ctx;
        }

        @Override
        public void onResponse(Bitmap response) {
            comment.setBitmap(response);
            updateProfileInfo(ctx, myComment);
        }
    }

    private void updateProfileInfo(Context ctx, Comment cmt){
        EditText input = (EditText) findViewById(R.id.input);
        ImageView pic = (ImageView) findViewById(R.id.prof_pic);
        TextView name = (TextView) findViewById(R.id.name);
        Button upload = (Button) findViewById(R.id.upload);
        /*FacebookUserInfo fb = new FacebookUserInfo(ctx);
        if(fb.isReady()){
            String profName = fb.getName();
            String userId = fb.getId();
            String picUrl = HelperMethods.getUserProfilePictureUrl(userId);
            System.out.println("ASd");
        }else{
            input.setEnabled(false);
            upload.setEnabled(false);
        }*/
        pic.setImageBitmap(cmt.getBitmap());
        name.setText(cmt.getName());
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
