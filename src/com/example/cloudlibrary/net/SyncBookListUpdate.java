package com.example.cloudlibrary.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ListView;
import com.example.cloudlibrary.adapters.CustomAdapter;
import com.example.cloudlibrary.helpers.ServiceAddresses;
import com.example.cloudlibrary.model.Book;
import com.example.cloudlibrary.parsers.BookListParser;
import com.example.cloudlibrary.volley.RequestQueue;
import com.example.cloudlibrary.volley.Response;
import com.example.cloudlibrary.volley.VolleyError;
import com.example.cloudlibrary.volley.toolbox.ImageRequest;
import com.example.cloudlibrary.volley.toolbox.JsonArrayRequest;
import com.example.cloudlibrary.volley.toolbox.Volley;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import java.util.ArrayList;

/**
 * Created by Jay on 3/16/14.
 */
public class SyncBookListUpdate {
    private Context ctx;
    private ProgressDialog progress;
    private ListView lv;

    public SyncBookListUpdate(Context ctx, ProgressDialog progress, ListView lv) {
        this.ctx = ctx;
        this.progress = progress;
        this.lv = lv;
    }

    public void makeRequest(double longitude, double latitude) {
        String url = generateUrl(longitude, latitude);
        RequestQueue queue = Volley.newRequestQueue(ctx);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progress.dismiss();
                        ArrayList<Book> bookList = BookListParser.getBookListFromResponse(response);
                        CustomAdapter adapt = (CustomAdapter) lv.getAdapter();
                        for(int i = 0; i < bookList.size(); i++){
                            Book book = bookList.get(i);
                            String k = ServiceAddresses.IP + book.getImageUrl();
                            RequestQueue queue = Volley.newRequestQueue(ctx);
                            Resp rsp = new Resp(book, adapt);
                            ImageRequest request = new ImageRequest(k, rsp, 0, 0, null, null);
                            queue.add(request);
                        }
                        adapt.setNewValues(bookList);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                    }
                }
        );
        queue.add(arrayRequest);
    }

    private class Resp implements Response.Listener<Bitmap>{
        private Book book;
        private CustomAdapter adapter;

        public Resp(Book book, CustomAdapter adapter){
            this.book = book;
            this.adapter = adapter;
        }

        @Override
        public void onResponse(Bitmap response) {
            book.setBitmap(response);
            adapter.notifyDataSetChanged();
        }
    }

    private String generateUrl(double longitude, double latitude) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

        list.add(new BasicNameValuePair("longitude", String.valueOf(longitude)));
        list.add(new BasicNameValuePair("latitude", String.valueOf(latitude)));

        return ServiceAddresses.IP + ServiceAddresses.BOOK_LIST_URL + URLEncodedUtils.format(list, "UTF-8");
    }
}
