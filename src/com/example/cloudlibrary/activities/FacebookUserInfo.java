package com.example.cloudlibrary.activities;

import android.content.Context;
import android.content.SharedPreferences;

public class FacebookUserInfo {
	
	private SharedPreferences prefs;
	
	public FacebookUserInfo(Context ctx){
		prefs = ctx.getSharedPreferences("pref", 0);
	}
	
	public String getId(){
		return prefs.getString("id", null);
	}
	
	public String getName(){
		return prefs.getString("name", null);
	}
	
	public boolean isReady(){
		return getId()!= null;
	}
	
}
