package com.example.cloudlibrary.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Jay on 3/15/14.
 */
public class Comment implements Serializable {
    private String id, name, date, comment, imageUrl;
    private Bitmap bitmap;
    private static final long serialVersionUID = 1L;

    public Comment(){
    }

    public Comment(String id, String name, String date, String comment, String imageUrl, Bitmap bitmap){
        this.id = id;
        this.name = name;
        this.date = date;
        this.comment = comment;
        this.imageUrl = imageUrl;
        this.bitmap = bitmap;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return this.date;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getComment(){
        return this.comment;
    }

    public void setImageUrl(String url){
        this.imageUrl = url;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }
}
