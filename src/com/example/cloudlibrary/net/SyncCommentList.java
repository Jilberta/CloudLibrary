package com.example.cloudlibrary.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

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
 * Created by Jay on 3/15/14.
 */
public class SyncCommentList {
    private Context ctx;
    private ProgressDialog progress;
    private int count;

    public SyncCommentList(Context ctx, ProgressDialog progress){
        this.ctx = ctx;
        this.progress = progress;
    }

    public void makeRequest(double longitude, double latitude){
        String url = generateUrl(longitude, latitude);
        RequestQueue queue = Volley.newRequestQueue(ctx);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progress.dismiss();
                ArrayList<Comment> commentList = CommentListParser.getCommentListFromResponse(response);
                count = commentList.size();
                Intent k = new Intent(ctx, CommentListActivity.class);
                k.putExtra("CommentList", commentList);
                ctx.startActivity(k);
//                Toast.makeText(ctx, response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String txt = new String(error.networkResponse.data) + " - " + error.networkResponse.statusCode;
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
}
