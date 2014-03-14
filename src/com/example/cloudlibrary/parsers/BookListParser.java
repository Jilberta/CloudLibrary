package com.example.cloudlibrary.parsers;

import com.example.cloudlibrary.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jay on 3/15/14.
 */
public class BookListParser {

    public static ArrayList<Book> getBookListFromResponse(JSONArray response){
        ArrayList<Book> bookList = new ArrayList<Book>();
        JSONObject obj;
        Book book;
        for(int i = 0; i < response.length(); i++){
            try {
                obj = response.getJSONObject(i);
                book = new Book();
                book.setAuthorInfo(obj.getString("Author"));
                book.setTitle(obj.getString("Title"));
                book.setBriefDescription(obj.getString("Description"));
                book.setImageUrl(obj.getString("Image_url"));
                book.setDownloadUrl(obj.getString("Download_url"));
                bookList.add(book);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bookList;
    }
}
