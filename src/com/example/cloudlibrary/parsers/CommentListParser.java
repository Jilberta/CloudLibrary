package com.example.cloudlibrary.parsers;

import com.example.cloudlibrary.model.Comment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Jay on 3/15/14.
 */
public class CommentListParser {

    public static ArrayList<Comment> getCommentListFromResponse(JSONArray response) {
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        JSONObject obj;
        Comment commentItem;
        for (int i = 0; i < response.length(); i++) {
            try {
                obj = response.getJSONObject(i);
                commentItem = new Comment();
                commentItem.setId(obj.getString("Id"));
                commentItem.setDate(obj.getString("Date"));
                commentItem.setComment(obj.getString("Comment"));
                commentList.add(commentItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return commentList;
    }

    public static ArrayList<Comment> getUpdatedCommentList(JSONObject response) {
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        JSONObject obj;
        JSONArray arr;
        Comment commentItem;
        try {
            arr = response.getJSONArray("arr");
            for (int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                commentItem = new Comment();
                commentItem.setId(obj.getString("Id"));
                commentItem.setDate(obj.getString("Date"));
                commentItem.setComment(obj.getString("Comment"));
                commentList.add(commentItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentList;
    }
}
