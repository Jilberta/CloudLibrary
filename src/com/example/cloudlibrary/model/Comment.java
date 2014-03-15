package com.example.cloudlibrary.model;

/**
 * Created by Jay on 3/15/14.
 */
public class Comment {
    private String name, date, comment;

    public Comment(){
    }

    public Comment(String name, String date, String comment){
        this.name = name;
        this.date = date;
        this.comment = comment;
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
}
