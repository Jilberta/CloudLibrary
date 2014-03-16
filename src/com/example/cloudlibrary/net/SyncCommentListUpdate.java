package com.example.cloudlibrary.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ListView;

import com.example.cloudlibrary.activities.CommentListActivity;
import com.example.cloudlibrary.adapters.CommentListViewAdapter;
import com.example.cloudlibrary.helpers.HelperMethods;
import com.example.cloudlibrary.helpers.ServiceAddresses;
import com.example.cloudlibrary.model.Comment;
import com.example.cloudlibrary.parsers.CommentListParser;
import com.example.cloudlibrary.volley.Request;
import com.example.cloudlibrary.volley.RequestQueue;
import com.example.cloudlibrary.volley.Response;
import com.example.cloudlibrary.volley.VolleyError;
import com.example.cloudlibrary.volley.toolbox.ImageRequest;
import com.example.cloudlibrary.volley.toolbox.JsonArrayRequest;
import com.example.cloudlibrary.volley.toolbox.JsonObjectRequest;
import com.example.cloudlibrary.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jay on 3/16/14.
 */
public class SyncCommentListUpdate {
    private Context ctx;
    private ProgressDialog progress;
    private ListView lv;

    public SyncCommentListUpdate(Context ctx, ProgressDialog progress, ListView lv){
        this.ctx = ctx;
        this.progress = progress;
        this.lv = lv;
    }

    public void makeRequest(double longitude, double latitude){
        String url = generateUrl(longitude, latitude);
        RequestQueue queue = Volley.newRequestQueue(ctx);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progress.dismiss();
                ArrayList<Comment> commentList = CommentListParser.getCommentListFromResponse(response);
                CommentListViewAdapter adapt = (CommentListViewAdapter) lv.getAdapter();
                for(int i = 0; i < commentList.size(); i++){
                    Comment comment = commentList.get(i);
                    String userId = comment.getId();
                    String url = ServiceAddresses.FACEBOOK_URL + comment.getId();
                    RequestQueue queue = Volley.newRequestQueue(ctx);
                    Resp2 rsp = new Resp2(comment, adapt);
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
                    RequestQueue queue = Volley.newRequestQueue(ctx);
                    Resp rsp = new Resp(comment, adapt);
                    ImageRequest request = new ImageRequest(k, rsp, 0, 0, null, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("ASd");
                        }
                    });
                    queue.add(request);
                }
                adapt.setNewValues(commentList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
            }
        });

//        progress = ProgressDialog.show(ctx, "", "Gtxovt Daicadot");
        queue.add(arrayRequest);
    }

    private String generateUrl(double longitude, double latitude){
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

        list.add(new BasicNameValuePair("longitude", String.valueOf(longitude)));
        list.add(new BasicNameValuePair("latitude", String.valueOf(latitude)));

        return ServiceAddresses.IP + ServiceAddresses.COMMENT_LIST_URL + URLEncodedUtils.format(list, "UTF-8");
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
}
