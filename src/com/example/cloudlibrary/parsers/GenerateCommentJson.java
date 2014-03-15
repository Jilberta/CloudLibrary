package com.example.cloudlibrary.parsers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jay on 3/15/14.
 */
public class GenerateCommentJson {

    public static JSONObject makeJson(String userId, String comment, String date, double longitude, double latitude){
        JSONObject res = new JSONObject();
        try {
            res.put("UserId", userId);
            res.put("Comment", comment);
            res.put("Date", date);
            res.put("Longitude", longitude);
            res.put("Latitude", latitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
}
