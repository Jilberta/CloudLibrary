package com.example.cloudlibrary.net;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.cloudlibrary.helpers.ServiceAddresses;
import com.example.cloudlibrary.volley.Request;
import com.example.cloudlibrary.volley.RequestQueue;
import com.example.cloudlibrary.volley.Response;
import com.example.cloudlibrary.volley.VolleyError;
import com.example.cloudlibrary.volley.toolbox.JsonObjectRequest;
import com.example.cloudlibrary.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jay on 3/16/14.
 */
public class SyncGeoCoder {
    private Context ctx;
    private ProgressDialog progress;

    public SyncGeoCoder (Context ctx, ProgressDialog progress){
        this.ctx = ctx;
        this.progress = progress;
    }

    public void makeRequest(double longitude, double latitude){
        String url = generateUrl(longitude, latitude);
        RequestQueue queue = Volley.newRequestQueue(ctx);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
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
        queue.add(request);
    }

    public String generateUrl(double longitude, double latitude){
        return ServiceAddresses.GOOGLE_GEOCODER_URL + String.valueOf(latitude) + "," + String.valueOf(longitude) + "&sensor=true";
    }
}
