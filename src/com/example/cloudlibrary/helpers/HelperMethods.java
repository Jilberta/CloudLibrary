package com.example.cloudlibrary.helpers;

import android.content.Context;

/**
 * Created by Jay on 3/16/14.
 */
public class HelperMethods {

    public static final String lashasId = "1057089246";
    public static final String lashaName = "Lasha Lakirbaia";

    public static String getUserProfilePictureUrl(String userId){
        return "https://graph.facebook.com/" + userId + "/picture?type=normal";
    }
}
