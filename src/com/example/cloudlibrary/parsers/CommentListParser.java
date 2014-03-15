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

    public static ArrayList<Comment> getCommentListFromResponse(JSONArray response){
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        JSONObject obj;
        Comment commentItem;
        for(int i = 0; i < response.length(); i++){
            try {
                obj = response.getJSONObject(i);
                commentItem = new Comment();
                commentItem.setName(obj.getString("Id"));
                commentItem.setDate(obj.getString("Date"));
                commentItem.setComment(obj.getString("Comment"));
                commentList.add(commentItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return commentList;
    }
}
