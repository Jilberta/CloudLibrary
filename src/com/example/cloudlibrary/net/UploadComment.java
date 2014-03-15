package com.example.cloudlibrary.net;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.cloudlibrary.helpers.ServiceAddresses;
import com.example.cloudlibrary.parsers.GenerateCommentJson;
import com.example.cloudlibrary.volley.Request;
import com.example.cloudlibrary.volley.RequestQueue;
import com.example.cloudlibrary.volley.Response;
import com.example.cloudlibrary.volley.VolleyError;
import com.example.cloudlibrary.volley.toolbox.JsonObjectRequest;
import com.example.cloudlibrary.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Jay on 3/15/14.
 */
public class UploadComment {
    Context ctx;
    ProgressDialog progress;

    public UploadComment(Context ctx){
        this.ctx = ctx;
    }

    public void makeRequest(String userId, String comment, String date, double longitude, double latitude){
        String url = ServiceAddresses.IP + ServiceAddresses.COMMENT_UPLOAD_URL;
        JSONObject obj = GenerateCommentJson.makeJson(userId, comment, date, longitude, latitude);

        RequestQueue queue = Volley.newRequestQueue(ctx);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progress.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                    }
        });

        progress = ProgressDialog.show(ctx, "", "მიმდინარეობს წარწერის დატოვება");
        queue.add(request);
    }
}
