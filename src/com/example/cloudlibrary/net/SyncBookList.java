package com.example.cloudlibrary.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.cloudlibrary.activities.BooksListActivity;
import com.example.cloudlibrary.helpers.ServiceAddresses;
import com.example.cloudlibrary.model.Book;
import com.example.cloudlibrary.parsers.BookListParser;
import com.example.cloudlibrary.volley.RequestQueue;
import com.example.cloudlibrary.volley.Response;
import com.example.cloudlibrary.volley.VolleyError;
import com.example.cloudlibrary.volley.toolbox.JsonArrayRequest;
import com.example.cloudlibrary.volley.toolbox.Volley;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import java.util.ArrayList;

/**
 * Created by Jay on 3/15/14.
 */
public class SyncBookList {
    private Context ctx;
    private ProgressDialog progress;

    public SyncBookList(Context ctx){
        this.ctx = ctx;
    }

    public void makeRequest(double longitude, double latitude){
        String url = generateUrl(longitude, latitude);
        RequestQueue queue = Volley.newRequestQueue(ctx);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progress.dismiss();
                    ArrayList<Book> bookList = BookListParser.getBookListFromResponse(response);
                    Intent k = new Intent(ctx, BooksListActivity.class);
                    k.putExtra("BookList", bookList);
                    ctx.startActivity(k);
//                    Toast.makeText(ctx, response.toString(), Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismiss();
                }
        });

        progress = ProgressDialog.show(ctx, "", "Gtxovt Daicadot");
        queue.add(arrayRequest);
    }

    private String generateUrl(double longitude, double latitude){
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

        list.add(new BasicNameValuePair("longitude", String.valueOf(longitude)));
        list.add(new BasicNameValuePair("latitude", String.valueOf(latitude)));

        return ServiceAddresses.IP + ServiceAddresses.BOOK_LIST_URL + URLEncodedUtils.format(list, "UTF-8");
    }

}
